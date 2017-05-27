package com.hello.cases;

import android.support.test.espresso.core.deps.guava.collect.Lists;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.hello.cases.model.Number;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Iterator;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class JsonTest {
    private static final String TAG = JsonTest.class.getSimpleName();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void jsonObjectTest() throws Exception {
        JSONObject outerJsonObj = new JSONObject();
        //disorder and will be replaced each other with the same key
        outerJsonObj.put("A", "aa");
        outerJsonObj.put("B", 5);
        outerJsonObj.put("C", "c,c\"d");
        outerJsonObj.put("D", true);
        //D will be replaced as false
        outerJsonObj.put("D", false);

        JSONObject innerJsonObjE = new JSONObject();
        innerJsonObjE.put("e", "e");
        innerJsonObjE.put("ee", "ee");
        outerJsonObj.put("E", innerJsonObjE);

        JSONObject innerJsonObjF = new JSONObject();
        innerJsonObjF.put("f", "f");
        innerJsonObjF.put("ff", "ff");
        outerJsonObj.put("F", innerJsonObjF.toString());

        Log.d(TAG, "jsonObjectTest outerJson:" + outerJsonObj.toString());
        //outerJson:{"A":"aa","B":5,"C":"c,c\"d","D":false,"E":{"e":"e","ee":"ee"},"F":"{\"f\":\"f\",\"ff\":\"ff\"}"}
        JSONObject outJsonObject = new JSONObject(outerJsonObj.toString());
        printJsonAllKeyValue(outJsonObject);

        boolean keyDValue = outJsonObject.getBoolean("D");
        Log.d(TAG, "jsonObjectTest key D and value:" + keyDValue);
        //key D and value:false
        JSONObject tmp = outerJsonObj.getJSONObject("E");
        printJsonAllKeyValue(tmp);

        //String cannot be converted to JSONObject
        //tmp = outerJsonObj.getJSONObject("F");

        String innerJsonFStr = outerJsonObj.getString("F");
        printJsonAllKeyValue(new JSONObject(innerJsonFStr));
    }

    @Test
    public void jsonArrayTest() throws Exception {
        JSONArray outerArray = new JSONArray();
        //order and will not be replaced each other
        outerArray.put("A");
        outerArray.put(5);
        outerArray.put(true);
        outerArray.put(false);

        JSONObject innerObject = new JSONObject();
        innerObject.put("B", "b");
        outerArray.put(innerObject);
        Log.d(TAG, "jsonArrayTest: " + outerArray.toString());
        printJsonAllIndex(outerArray);
        //index:0 A
        //index:1 5
        //index:2 true
        //index:3 false
        //index:4 {"B":"b"}
    }


    /**
     * 场景如ContentProvider 需要传递一个数据结构Contact其中有多个号码 号码对应一个label姑且叫name
     * name可能为空或重复，此时不能遍历号码去通过jsonObject.put 因为后面的号码可能会将之前的覆盖
     * 而且有考虑到Object.put是无序的 因此我们可以通过index从1开始进行添加 获取时从1去get
     * 从而转无序为有序
     *
     * @throws Exception
     */
    @Test
    public void jsonNumberTest() throws Exception {
        //一个联系人里面的多个号码
        List<Number> inputNumberList = Lists.newArrayList();
        inputNumberList.add(new Number("A", "11"));
        inputNumberList.add(new Number("B", "22"));
        inputNumberList.add(new Number("A", "33"));

        Log.d(TAG, "inputNumberList: " + dumpNumberList(inputNumberList));
        //inputNumberList: (A,11)(B,22)(A,33)
        JSONObject inputJson = new JSONObject();
        for (int i = 0; i < inputNumberList.size(); i++) {
            JSONArray map = new JSONArray();
            map.put(inputNumberList.get(i).getName());
            map.put(inputNumberList.get(i).getValue());
            inputJson.put(String.valueOf(i), map.toString());
        }
        Log.d(TAG, "inputJson:" + inputJson.toString());
        //inputJson:{"0":"[\"A\",\"11\"]","1":"[\"B\",\"22\"]","2":"[\"A\",\"33\"]"}
        //sending....

        //receiver string inputJson
        List<Number> outputNumberList = Lists.newArrayList();
        String receivedStr = inputJson.toString();
        JSONObject receivedObject = new JSONObject(receivedStr);
        Iterator it = receivedObject.keys();

        while (it.hasNext()) {
            String key = (String) it.next();
            String mapString = receivedObject.getString(key);
            JSONArray map = new JSONArray(mapString);
            outputNumberList.add(new Number((String) map.get(0), (String) map.get(1)));
        }
        Log.d(TAG, "outputNumberList: " + dumpNumberList(outputNumberList));
        //outputNumberList: (A,11)(B,22)(A,33)
    }


    @Test
    public void string2JsonTest() throws Exception {
        String jsonString = "{\"name\":\"Annan\",\"age\":30}";
        JSONObject json = new JSONObject(jsonString);
        Log.d(TAG, "string2JsonTest: name:" + json.get("name") + " age:" + json.get("age"));
        // name:Annan age:30
        printJsonAllKeyValue(json);
    }

    @Test
    public void jsonStringerTest() throws Exception {
        JSONStringer jsonStringer = new JSONStringer().object()
                .key("A").value("a")
                .key("B").value(true)

                //注意这里有个array的key啊
                .key("ArrayKey");
                jsonStringer.array();
                jsonStringer.object()
                        .key("C").value("c")
                        .endObject();
                jsonStringer.object()
                         .key("D").value("d")
                         .endObject();
                jsonStringer.endArray();

                jsonStringer.key("E").value(10);
                jsonStringer.endObject();
        Log.d(TAG, "jsonStringerTest: jsonStringer:" + jsonStringer.toString());
    }

    @Test
    public void jsonTokenerTest() throws Exception {
        String jsonStr = "{\"age\":190,\"sex\":\"男\",\"userName\":\"周伯通\"}";
        JSONTokener jsonTokener = new JSONTokener(jsonStr);
        JSONObject jsonObj;
        String userName;
        String sex;
        int age;
        try {
            jsonObj = (JSONObject) jsonTokener.nextValue();
            userName = jsonObj.getString("userName");
            age = jsonObj.getInt("age");
            sex = jsonObj.getString("sex");
            Log.d(TAG, "jsonTokenerTest: userName:" + userName + " age:" + age + " sex:" + sex);
            //userName:周伯通 age:190 sex:男
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void printJsonAllKeyValue(JSONObject object) throws Exception {
        Iterator it = object.keys();
        while (it.hasNext()) {
            String key = (String) it.next();
            Log.d(TAG, "key:" + key + " value:" + object.get(key));
        }
    }

    private void printJsonAllIndex(JSONArray object) throws Exception {
        for (int i = 0; i < object.length(); i++) {
            Log.d(TAG, "index:" + i + " " + object.get(i));
        }
    }

    private String dumpNumberList(List<Number> numberList) {
        int size = numberList.size();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < size; i++) {
            sb.append("(" + numberList.get(i).getName() + "," + numberList.get(i).getValue() + ")");
        }
        return sb.toString();
    }

}
