package com.algorithm;

import java.util.*;

@FunctionalInterface
interface OpInterface {
    int op(int a, int b);
}

class Solution {
    public List<List<String>> findLadders(String start, String end, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(end)) return res;

        Map<String, List<String>> adj = new HashMap<>();
        Map<String, Integer> dists = new HashMap<>();
        Queue<String> q = new LinkedList<>(Arrays.asList(start));
        dists.put(start, 0);

        // BFS: Build the Graph (DAG)
        while (!q.isEmpty()) {
            String curr = q.poll();
            if (curr.equals(end)) break;

            for (int i = 0; i < curr.length(); i++) {
                char[] chars = curr.toCharArray();
                for (char c = 'a'; c <= 'z'; c++) {
                    chars[i] = c;
                    String next = new String(chars);

                    if (dict.contains(next)) {
                        if (!dists.containsKey(next)) {
                            dists.put(next, dists.get(curr) + 1);
                            q.offer(next);
                        }
                        // Only add edge if it's a shortest path step
                        if (dists.get(next) == dists.get(curr) + 1) {
                            adj.computeIfAbsent(curr, k -> new ArrayList<>()).add(next);
                        }
                    }
                }
            }
        }

        dfs(start, end, adj, new ArrayList<>(Arrays.asList(start)), res);
        return res;
    }

    private void dfs(String curr, String end, Map<String, List<String>> adj, List<String> path, List<List<String>> res) {
        if (curr.equals(end)) {
            res.add(new ArrayList<>(path));
            return;
        }
        if (adj.containsKey(curr)) {
            for (String next : adj.get(curr)) {
                path.add(next);
                dfs(next, end, adj, path, res);
                path.remove(path.size() - 1);
            }
        }
    }

    public Map<String, List<String>> memo = new HashMap<>();

    public List<String> wordBreak(String s, List<String> wordDict) {
        if (memo.containsKey(s)) {
            return memo.get(s);
        }

        List<String> res = new LinkedList<>();
        if (s.isEmpty()) {
            res.add("");
            return res;
        }

        for (String word : wordDict) {
            if (s.startsWith(word)) {
                List<String> sublist = wordBreak(s.substring(word.length()), wordDict);
                for (String sub : sublist) {
                    res.add(word + (sub.isEmpty() ? "" : " ") + sub);
                }
            }
        }

        memo.put(s, res);
        return res;
    }
}

class EngagementTracker {
    private final Map<Integer, Integer> counts = new HashMap<>();
    private final TreeMap<Integer, Set<Integer>> ranking = new TreeMap<>(Collections.reverseOrder());

    public void logEvent(int userId) {
        int oldFreq = counts.getOrDefault(userId, 0);
        int nweFreq = oldFreq + 1;
        counts.put(userId, nweFreq);
        if (oldFreq > 0) {
            ranking.get(oldFreq).remove(userId);
        }

        if (ranking.containsKey(oldFreq) && ranking.get(oldFreq).isEmpty()) {
            ranking.remove(oldFreq);
        }
        ranking.computeIfAbsent(nweFreq, k -> new HashSet<>()).add(userId);
    }

    public List<Integer> getTopK(int k) {
        List<Integer> topK = new ArrayList<>();
        for (Set<Integer> users : ranking.values()) {
            for (int userId : users) {
                if (topK.size() >= k) {
                    return topK;
                }
                topK.add(userId);
            }
        }
        return topK;
    }

    public void reset(int userId) {
        Integer freq = counts.remove(userId);
        if (freq != null && ranking.containsKey(freq))  {
            ranking.get(freq).remove(userId);
            if (ranking.get(freq).isEmpty()) {
                ranking.remove(freq);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        System.out.println("Output1: " + solution.findLadders("hit", "cog", Arrays.asList("hot","dot","dog","lot","log","cog")));
//        System.out.println("Output2: " + solution.findLadders("hit", "cog", Arrays.asList("hot","dot","dog","lot","log")));
//
//        System.out.println(solution.wordBreak("catsanddog",
//                Arrays.asList("cat","cats","and","sand","dog")));
//        System.out.println(solution.wordBreak("pineapplepenapple",
//                Arrays.asList("apple","pen","applepen","pine","pineapple")));
//        System.out.println(solution.wordBreak("catsandog",
//                Arrays.asList("cats","dog","sand","and","cat")));
//
//        EngagementTracker engagementTracker = new EngagementTracker();
//        engagementTracker.logEvent(5);
//        engagementTracker.logEvent(5);
//        engagementTracker.logEvent(5);
//        engagementTracker.logEvent(5);
//        engagementTracker.logEvent(4);
//        engagementTracker.logEvent(4);
//        engagementTracker.logEvent(4);
//        engagementTracker.logEvent(3);
//        engagementTracker.logEvent(3);
//        engagementTracker.logEvent(3);
//        engagementTracker.logEvent(3);
//        engagementTracker.logEvent(3);
//        engagementTracker.logEvent(3);
//        engagementTracker.logEvent(3);
//        engagementTracker.logEvent(3);
//        engagementTracker.logEvent(3);
//        engagementTracker.logEvent(2);
//        engagementTracker.logEvent(2);
//        engagementTracker.logEvent(1);
//        System.out.println(engagementTracker.getTopK(3));
//        engagementTracker.reset(5);
//        System.out.println(engagementTracker.getTopK(3));
//        String a = "abc";
//        String b = a.intern();
//        String c = "abc";
//        String d = new String("abc");
//        System.out.println(a.hashCode());
//        System.out.println(b.hashCode());
//        System.out.println(c.hashCode());
//        System.out.println(d.hashCode());
//        System.out.println(a == b);
//        System.out.println(a == c);
//        System.out.println(a == d);
//        System.out.println(a.equals(b));
//        System.out.println(a.equals(c));
//        System.out.println(a.equals(d));
//        OpInterface addInterface = Integer::sum;
//        OpInterface compInterface = Integer::compare;
//        System.out.println(addInterface.op(3, 5));
//        System.out.println(compInterface.op(3, 5));
//        System.out.println(compInterface.op(5, 3));
//        System.out.println(compInterface.op(3, 3));
        System.out.println(Boolean.valueOf("Yes"));
        RecordExample recordExample = new RecordExample(10);
        System.out.println(recordExample.n());
        List<Integer> l = Arrays.asList(2, 7, 4, 13, -6, 1, 5);
        System.out.println(l.stream().max(Integer::compareTo));
        l = Arrays.asList(2, 7, 4, 13, -6, 1, 5);
        System.out.println(l.stream().min(Integer::compareTo));
        l = Arrays.asList(2, 7, 4, 13, -6, 1, 5);
        System.out.println(l.stream().sorted().toList());
        l = Arrays.asList(2, 7, 4, 13, -6, 1, 5);
        System.out.println(l.stream().filter(n -> (n % 2 == 0)).count());
        l = Arrays.asList(2, 7, 4, 13, -6, 1, 5);
        System.out.println(l.stream().reduce((a, b) -> a * b));
        l = Arrays.asList(2, 7, 4, 13, -6, 1, 5);
        System.out.println(l.stream().reduce((a, b) -> a + b));
    }
}
