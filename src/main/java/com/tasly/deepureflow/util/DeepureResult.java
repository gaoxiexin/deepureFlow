package com.tasly.deepureflow.util;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 帝泊洱流向系统自定义响应结构
 */
public class DeepureResult {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private boolean status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public DeepureResult() {
    }
    
    public DeepureResult(Object data) {
        this.status = true;
        this.msg = "success";
        this.data = data;
    }
    
    public DeepureResult(boolean status, String msg) {
        this.status = status;
        this.msg = msg;
        this.data = null;
    }
    
    public DeepureResult(boolean status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
    
    public static DeepureResult addResult(boolean flag) {
    	if(flag){
    		 return new DeepureResult(flag,"新增成功");
    	}else{
    		return new DeepureResult(flag, "新增失败");
    	}
    }
    
    public static DeepureResult editResult(boolean flag) {
    	if(flag){
    		 return new DeepureResult(flag,"修改成功");
    	}else{
    		return new DeepureResult(flag, "修改失败");
    	}
    }
    
    public static DeepureResult delResult(boolean flag) {
    	if(flag){
    		 return new DeepureResult(flag,"删除成功");
    	}else{
    		return new DeepureResult(flag, "删除失败");
    	}
    }
    
    public static DeepureResult importResult(boolean flag) {
    	if(flag){
    		 return new DeepureResult(flag,"文件上传成功");
    	}else{
    		return new DeepureResult(flag, "文件上传失败");
    	}
    }
    
    public static DeepureResult result(boolean flag,String msg) {
    	if(flag){
    		 return new DeepureResult(flag,msg);
    	}else{
    		return new DeepureResult(flag, msg);
    	}
    }
    
    public static DeepureResult result(boolean flag,String msg,Object data) {
    	return new DeepureResult(flag,msg,data);
    }
    
   public static DeepureResult success() {
    	return new DeepureResult(null);
    }
   public static DeepureResult fail() {
       return new DeepureResult(false, "fail");
   }
    
    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将json结果集转化为DeepureResult对象
     * 
     * @param jsonData json数据
     * @param clazz DeepureResult中的object类型
     * @return
     */
    public static DeepureResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, DeepureResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return result(jsonNode.get("status").asBoolean(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有object对象的转化
     * 
     * @param json
     * @return
     */
    public static DeepureResult format(String json) {
        try {
            return MAPPER.readValue(json, DeepureResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     * 
     * @param jsonData json数据
     * @param clazz 集合中的类型
     * @return
     */
    public static DeepureResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return result(jsonNode.get("status").asBoolean(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

}
