package com.xss.json;

import com.xss.util.U;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
public class JsonResult {

    public static final String DATA = "data";
    public static final String[] RETURN = new String[] {"code", "msg", "userId", "token", DATA};

    /** 返回码 */
    public static enum Code {
        /** 失败 */
        FAIL(0),
        /** 成功 */
        SUCCESS(1),
        /** 律师未认证 */
        NO_AUTH(100),
        /** 未授权 **/
        UNAUTHORIZED(401),
        /** 未登录 */
        NO_LOGIN(10);

        int flag;
        Code(int flag) { this.flag = flag; }
        public int getFlag() { return flag; }

        public static int getFlag(boolean success) {
            return success ? SUCCESS.flag : FAIL.flag;
        }
        public static boolean contains(int code) {
            for (Code ce : values()) {
                if (ce.flag == code) return true;
            }
            return false;
        }
    }

    /** 返回编码. 是否成功、是否登录等. 必须有值 */
    private int code = Code.FAIL.flag;

    /** 返回说明. 必须有值, 然而阻止不了传递 null 或者空字符 */
    private String msg = U.EMPTY;

    /** 返回数据. 可以是对象(User, 等同于 Map), 可以是数组(List&lt;User&gt;) */
    private Object data;

    private Long userId;
    private String token;


    private JsonResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /** 返回码及信息 */
    private JsonResult(Code code, String msg) {
        this(code, msg, null);
    } 
    private JsonResult(Code code, String msg, Object data) {
        this(code.getFlag(), msg, data);
    }

    public JsonResult setCode(Code code) {
        this.code = code.getFlag();
        return this;
    }
    public JsonResult setCode(int code) {
        // 只在里面的才能被赋值
        if (Code.contains(code)) {
            this.code = code;
        }
        return this;
    }

    public JsonResult setInfo(Long userId, String token) {
        if (U.greater0(userId) && U.isNotBlank(token)) {
            // 用户 id 和 token 只在没值的情况下才添加, 避免被覆盖!
            if (U.less0(this.userId)) {
                this.userId = userId;
            }
            if (U.isBlank(this.token)) {
                this.token = token;
            }
        }
        return this;
    }

    // ---------- 下面的静态方法, 在 controller 中调用 ----------

    /** 成功时要有 msg 说明 */
    public static JsonResult success(String msg) {
        return new JsonResult(Code.SUCCESS, msg);
    }

    /** 返回的数据不需要过滤属性时 */
    public static JsonResult success(String msg, Object data) {
        return new JsonResult(Code.SUCCESS, msg, data);
    }

    /** 返回的数据需要过滤属性时 */
    public static JsonResult success(String msg, Object data, String... params) {
        return new JsonResult(Code.SUCCESS, msg, JsonUtil.toObjectWithField(data, params));
    }


    /** 失败时要有 msg 说明 */
    public static JsonResult fail(String msg) {
        return new JsonResult(Code.FAIL, msg);
    }
    
    /** 错误状态指定相应状态码 **/
    public static JsonResult fail(Code code,String msg) {
        return new JsonResult(code, msg);
    }

    /** 未授权 **/
    public static JsonResult unauthorized() {
        return new JsonResult(Code.UNAUTHORIZED, "无权限");
    }


    /** 未登录时 */
    public static JsonResult notLogin() {
        return notLogin(null);
    }
    /** 未登录时 */
    public static JsonResult notLogin(Object data) {
        return new JsonResult(Code.NO_LOGIN, "请先登录", data);
    }
    /** 律师未认证时 */
    public static JsonResult notAuth() {
        return new JsonResult(Code.NO_AUTH, "您还未认证, 无法操作此步骤!");
    }
}
