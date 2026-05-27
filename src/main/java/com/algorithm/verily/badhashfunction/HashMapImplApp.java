package com.algorithm.verily.badhashfunction;

class HashMapImplApp {
    public static void main(String[] args) {
        // store key (String) value (any Object) pairs
        HashMapImpl hashMapImpl = new HashMapImpl();
        hashMapImpl.put("id_101", "foobar1");
        hashMapImpl.put("id_102", "foobar2");

        // retrive values from their keys
        System.out.println(hashMapImpl.get("id_101"));
        System.out.println(hashMapImpl.get("id_102"));

         // remove a key if it exists
         hashMapImpl.remove("id_101");

        // retrive values from their keys
        System.out.println(hashMapImpl.get("id_101"));
        System.out.println(hashMapImpl.get("id_102"));

        //set k1 -> v1
        //set k2 -> v2
        //get k1 returns v1
        //get k2 returns v2
    }
}
