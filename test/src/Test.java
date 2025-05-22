import java.util.*;

public class Test {
    public static void main(String[] args) {
        Test t=new Test();
        List<String> input=new ArrayList<>();
        String[] arr={"apple","banana","apple","orange","banana","grape","grape"};
        Collections.addAll(input, arr);
        Map<String, Integer> res = t.f3(input);

        System.out.println(res);

//        Map<String,Integer> input=new HashMap<>();
//        input.put("John", 35);
//        input.put("Bob", 40);
//        input.put("Alice", 30);
//        input.put("Tom",45);
//        input.put("Jerry",50);
//
//        List<String> res=t.f2(input);
//        System.out.println(res);


    }

    public List<String> f1(List<String> input){
        Map<String,Object> map=new HashMap<>();
        List<String> res=new ArrayList<>();
        for (String s : input) {
            if (map.get(s) == null) {
                map.put(s, 1);
                res.add(s);
            }
        }
        return res;
    }

    public List<String> f2(Map<String,Integer> input){
        //找到值最大的三个key
        PriorityQueue<Map.Entry<String,Integer>> pq=new PriorityQueue<>(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue()-o2.getValue();
            }
        });
        for (Map.Entry<String,Integer> entry:input.entrySet()) {
            if (pq.size()<3){
                pq.add(entry);
            }else {
                if (entry.getValue()>pq.peek().getValue()){
                    pq.poll();
                    pq.add(entry);
                }
            }
        }

        List<String> res=new ArrayList<>();
        while (!pq.isEmpty()){
            res.add(pq.poll().getKey());
        }
        return res;
    }

    public Map<String,Integer> f3(List<String> input){
        Map<String,Integer> res=new HashMap<>();

        for(String s:input){
            res.merge(s, 1, Integer::sum);
        }

        return res;
    }

    public List<List<String>> f4(List<String> input){
        List<String> num=new ArrayList<>();
        List<String> word=new ArrayList<>();
        for(String s:input){
            //如果只包含数字
            if(s.matches("[0-9]+")){
                num.add(s);
            }
            else{
                //如果只包含字母
                if(s.matches("[a-zA-Z]+")){
                    word.add(s);
                }
            }
        }
        List<List<String>> res=new ArrayList<>();
        res.add(num);
        res.add(word);
        return res;
    }

    public Map<String,Integer> f5(Map<String,List<Integer> input){
        Map<String,Integer> res=new HashMap<>();

        List<String> names=new ArrayList<>();
        for(Map.Entry<String,List<Integer>> entry:input.entrySet()){
            names.add(entry.getKey());
        }

        for(String name:names){
            List<Integer> score=input.get(name);
            int total=0;
            for (Integer integer : score) {
                total += integer;
            }
            total=total/score.size();
            res.put(name,total);
        }
        return res;
    }
    public Map<String,String> f6(List<String> input){
        Map<String,String> res=new HashMap<>();
        for(String s:input){
            String[] arr=s.split(",");
            res.put(arr[1],arr[0]);
        }
        return res;
    }
}