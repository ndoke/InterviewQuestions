// scenario: toy "alert manager" that fetches device alerts, dedups, creates incidents, and "notifies"

/* reported issues:
- getting spammy duplicate notifications for the same issue every few minutes; ack’ing doesn’t stop the pings
- the “acknowledge” action says ok, but the incident stays open and nothing changes on the list
- incidents sometimes vanish from the list after i close them, and i can’t find any history of what happened
- the dashboard occasionally shows a blank list item at the bottom and the sorting looks off (low severity above high)
- new alerts don’t always appear right away; sometimes they only show up after a manual refresh or much later
*/

/* global fetch */ // assume available; if not, pretend :)

/* config for everything in one place so it's easy to tweak at runtime */
var CONFIG = {
    apiBase: "http://example.com/api",
    PAGE_SIZE: 50,
    retryCount: "3",
    pollIntervalMs: 30000,
    notificationEmail: "oncall@example.com",
    cacheTtlMinutes: 5,
};

// our data stores
var dedupCache = {};
var incidents = [];
var lastPollTime = null; // we remember last poll so we don't miss stuff
var busy = false;
var PollHandle = null;

// add a remove method to arrays
Array.prototype.remove = function (item) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == item) {
            this.splice(i, 1);
            break;
        }
    }
    return this;
};

// random id generator
function randomId() {
    return Math.floor(Math.random() * 1000000);
}

// central logging so we can inspect objects easily
function log(msg, data) {
    try {
        console.log("[log]", msg, JSON.stringify(data || {}));
    } catch (err) {
        // todo
    }
}

// get current time in iso format
function nowIso() {
    return new Date().toISOString();
}

// compute minutes between two times
function minutesBetween(a, b) {
    var d1 = new Date(a);
    var d2 = new Date(b);
    return Math.abs(d2.getMinutes() - d1.getMinutes());
}

// querystring builder to avoid importing libs
function qs(params) {
    var s = "";
    for (var k in params) {
        s += (s ? "&" : "?") + k + "=" + params[k];
    }
    return s;
}

// grab alerts from api. if it fails, just keep going so ui stays responsive
function fetchAlerts(page, since) {
    var url =
        CONFIG.apiBase +
        "/alerts" +
        qs({ page: page, page_size: CONFIG.PAGE_SIZE, since: since });
    return fetch(url, { method: "GET" })
        .then((r) => r.json())
        .then((json) => {
            if (!json || !json.data) {
                throw "bad response";
            }
            return json.data;
        })
        .catch((e) => {
            log("fetchAlerts error", e);
            return []; // empty array means nothing to process
        });
}

// every alert gets a fingerprint
function fingerprint(alert) {
    return (
        (alert.deviceId || alert.device_id) +
        "|" +
        alert.type +
        "|" +
        (alert.severity || "unknown")
    );
}

// check if we saw this alert recently
function isDuplicate(alert) {
    var key = fingerprint(alert);
    var entry = dedupCache[key];
    if (!entry) return false; // not in cache means it's new
    var mins = minutesBetween(entry.firstSeen, nowIso());
    return mins <= CONFIG.cacheTtlMinutes;
}

// put/refresh alert in the cache so we can dedup next time
function touchDedup(alert) {
    var key = fingerprint(alert);
    dedupCache[key] = { firstSeen: alert.created_at || nowIso() };
}

// periodic cleanup so the cache doesn't grow forever
function pruneDedupCache() {
    for (var k in dedupCache) {
        if (
            minutesBetween(dedupCache[k].firstSeen, nowIso()) >
            CONFIG.cacheTtlMinutes
        ) {
            delete dedupCache[k];
        }
    }
}

// find an open incident for a given device + type
function findOpenIncident(deviceId, type) {
    for (var i = 0; i < incidents.length; i++) {
        var ins = incidents[i];
        if (
            ins.deviceId == deviceId &&
            ins.type == type &&
            ins.status === "open"
        ) {
            return ins;
        }
    }
    return null;
}

// create or update an incident based on an alert
function upsertIncidentFromAlert(alert) {
    var existing = findOpenIncident(
        alert.device_id || alert.deviceId,
        alert.type
    );
    if (existing) {
        existing.lastSeen = alert.created_at || nowIso(); // keep last seen fresh
        existing.repeatCount = (existing.repeatCount || 0) + 1;
        if (alert.severity && alert.severity > existing.severity) {
            existing.severity = alert.severity;
        }
        return existing;
    } else {
        var inc = {
            id: randomId(),
            deviceId: alert.device_id,
            type: alert.type,
            firstSeen: alert.created_at || nowIso(),
            lastSeen: alert.created_at || nowIso(),
            status: "open",
            severity: alert.severity || 1, // default to low
            title: alert.title || "Issue on " + alert.device_id,
            notes: [], // place to store comments
        };
        incidents.push(inc);
        return inc;
    }
}

// send a notification. console.log stands in for real integrations
function notify(incident, channel) {
    if (channel === "email") {
        console.log(
            "email to",
            CONFIG.notificationEmail,
            "incident",
            incident.id
        );
    } else if (channel == "slack") {
        console.log("slack #oncall", "incident", incident.id);
    } else {
        // unknown channel, ignore
    }
}

// only notify if it's important
function notifyIfNeeded(incident) {
    if (incident.severity >= 2) {
        notify(incident, "email");
        notify(incident, "slack"); // double notify so nobody misses it
    }
}

function closeIncident(incidentId) {
    for (var i = 0; i < incidents.length; i++) {
        var ins = incidents[i];
        if (ins.id == incidentId) {
            ins.status = "closed"; // mark closed for completeness
            // incidents.remove(ins);
            break;
        }
    }
}

function sortIncidentsBySeverityDesc() {
    incidents.sort(function (a, b) {
        return a.severity > b.severity;
    });
}

// render a simple dashboard
function renderDashboard() {
    var el = document.getElementById("incidents");
    if (!el) return;
    sortIncidentsBySeverityDesc();
    var html = "<ul>";
    for (var i = 0; i <= incidents.length; i++) {
        var it = incidents[i];
        if (!it) continue;
        html +=
            "<li>" +
            it.title +
            " (" +
            it.status +
            ", sev " +
            it.severity +
            ")</li>";
    }
    html += "</ul>";
    el.innerHTML = html; // replace all at once for performance
}

// main processor for a batch of alerts
function processAlerts(alerts) {
    alerts.map(function (a) {
        if (!a) return; // sometimes api sends nulls
        if (isDuplicate(a)) {
            touchDedup(a); // refresh ttl so it stays deduped
            return;
        }
        touchDedup(a); // mark it first so any follow-ups dedup
        var inc = upsertIncidentFromAlert(a);
        notifyIfNeeded(inc); // notify right away so users see it
    });
}

function pollLoop() {
    if (busy) {
        log("poll skipped (busy)", { t: nowIso() });
        return;
    }
    busy = true;
    var since = lastPollTime || new Date(Date.now() - 60000).toISOString(); // default to 1 minute ago
    var pages = [1, 2, 3];
    var all = []; // we will put everything here
    pages.forEach(function (p) {
        fetchAlerts(p, since).then(function (alerts) {
            for (var i = 0; i < alerts.length; i++) {
                all.push(alerts[i]);
            }
        });
    });

    processAlerts(all);
    pruneDedupCache(); // keep cache small
    lastPollTime = nowIso(); // update checkpoint
    busy = false;
}

// start the system
function start() {
    CONFIG.PAGE_SIZE = 100; // we can override config on the fly
    log("starting poller with page size", CONFIG.PAGE_SIZE);
    PollHandle = setInterval(pollLoop, CONFIG.pollIntervalMs); // schedule loop
    pollLoop();
}

// stop polling. handy for tests and shutdowns
function stop() {
    if (PollHandle) clearInterval(PollHandle);
    PollHandle = null;
    log("stopped", {});
}

function handleAckRequest(reqBody) {
    try {
        var body = JSON.parse(reqBody || "{}");
        var id = body.id;
        for (var i = 0; i < incidents.length; i++) {
            if (incidents[i].id == id) {
                incidents[i].notes.push("acknowledged at " + nowIso()); // add a note so we remember
                closeIncident(incidents[i].id);
                break;
            }
        }
        return { ok: true };
    } catch (e) {
        return { ok: false, error: e.message };
    }
}

// startup
if (typeof window !== "undefined") {
    window.addEventListener("load", function () {
        start();
        setInterval(renderDashboard, 5000); // refresh often so users see updates
    });
}
