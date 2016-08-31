package com.lychee.formviewtest.lychee;

/**
 * Created by lychee on 2015/10/27.
 */
public class ErrorCodeTransform {

    /**
     * 返回码处理
     *
     * @param errorCode
     * @return
     */
    public static String Transform(String errorCode) {
        int Code = Integer.parseInt(errorCode);
        switch (Code) {
//            case 5:
//                LogUtil.d("PHP is OK!");
//                return "";
            case 100000:
                return "系统错误（未处理异常）";
            case 100001:
                return "参数解析错误";
            case 100002:
                return "参数不全";
            case 100003:
                return "参数不正确";
            case 100004:
                return "用户未登录";
            case 100005:
                return "程序操作数据库异常";
            case 100006:
                return "实体不存在";
            case 100007:
                return "实体方法不存在";
            case 100008:
                return "接口执行错误";
            case 100009:
                return "代码错误";
            case 100010:
                return "用户数据不存在";
            case 100011:
                return "未支持的业务请求(reqType)类型";
            case 100099:
                return "没有赛事审核权限";
            case 101001:
                return "手机号格式不正确";
            case 101002:
                return "邮箱格式不正确";
            case 101003:
                return "手机号已注册";
            case 101004:
                return "手机号尚未注册";
            case 101005:
                return "邮箱已注册";
            case 101006:
                return "邮箱尚未注册";
            case 101007:
                return "验证码申请超出单日最大限度";
            case 101008:
                return "规定时间间隔内只能申请一次短信验证码";
            case 101009:
                return "密码不正确";
            case 101010:
                return "用户名或密码不正确";
            case 101011:
                return "该帐号尚未激活";
            case 101012:
                return "该帐号被限制登录";
            case 101015:
                return "系统发送验证码失败";
            case 101016:
                return "未选择验证码请求类型";
            case 101017:
                return "未支持的验证码请求类型";
            case 101019:
                return "未支持的注册类型";
            case 101020:
                return "未选择注册类型";
            case 101021:
                return "验证码错误";
            case 101022:
                return "未提供手机验证码";
            case 101025:
                return "新增用户记录失败";
            case 101030:
                return "旧密码未提供";
            case 101031:
                return "新密码未提供";
            case 101032:
                return "新密码格式不正确";
            case 101033:
                return "旧密码错误";
            case 101034:
                return "未匹配到用户手机号";
            case 101035:
                return "更新密码出错";
            case 101040:
                return "已设置过用户名";
            case 101041:
                return "未选择性别";
            case 101042:
                return "未填写生日信息";
            case 101044:
                return "用户名格式不正确";
            case 101045:
                return "用户名已存在";
            case 101046:
                return "手机号和身份证号为空";
            case 101047:
                return "不存在当前用户";
            case 101048:
                return "身份证不正确";
            case 101049:
                return "头像地址不正确";
            case 101050:
                return "修改信息失败";
            case 101051:
                return "已经绑定账户";
            case 101052:
                return "该用户已经被其他账号绑定";
            case 101053:
                return "用户未绑定账号";
            case 101054:
                return "已经绑定";
//            case 101055:
//                return "用户名已认领";
            case 101056:
                return "用户名已认领";
            case 101057:
                return "获取用户信息失败";
            case 101058:
                return "用户已激活";
            case 101059:
                return "用户更改失败";

            case 102001:
                return "未提供赛事Id";
            case 102002:
                return "赛事评论不能为空";
            case 102003:
                return "没有该赛事管理权限";
            case 102004:
                return "赛事名称过短";
            case 102005:
                return "赛事名称过长";
            case 102006:
                return "当前赛事已经结束";
            case 102007:
                return "已经申请中";
            case 102008:
                return "已经申请成功";
            case 102009:
                return "已经通过审核";
            case 102010:
                return "该用户没有申请成为裁判";
            case 102011:
                return "审核未通过";
            case 102012:
                return "未提供申请成为裁判的用户Id";
            case 102013:
                return "未选择审核类型";
            case 102014:
                return "未支持审核类型";
            case 102015:
                return "已通过此审批";
            case 102016:
                return "阶段详表Id为空";
            case 102017:
                return "对抗分组Id为空";
            case 102018:
                return "写入赛事出错";
            case 102019:
                return "写入赛事图片出错";
            case 102020:
                return "写入赛事举办项目承办单位信息失败";
            case 102021:
                return "写入赛事项目失败";
            case 102022:
                return "设置项目时间失败";
            case 102023:
                return "创建者获取权限失败";
            case 102024:
                return "赛事介绍过短";
            case 102025:
                return "赛事秩序表Id为空";
            case 102026:
                return "团队比赛Id为空";
            case 102027:
                return "团队Id为空";
            case 102028:
                return "运动类型为空";
            case 102029:
                return "对抗分组轮次Id为空";
            case 102030:
                return "选手Id为空";
            case 102031:
                return "选手数据为空";
            case 102032:
                return "未满足当前项目规则";
            case 102033:
                return "该用户或者手机号已经被新增";
            case 102034:
                return "当前团队已经有人报名";
            case 102035:
                return "修改资料失败";
            case 102036:
                return "审核失败";
            case 102037:
                return "双打选手Id为空";
            case 102038:
                return "查找不到用户Id";
            case 102039:
                return "球馆Id为空";
            case 102040:
                return "赛事开始日期非法";
            case 102041:
                return "赛事结束日期非法";
            case 102042:
                return "图片路径数据为空";
            case 102043:
                return "事务执行出错";
            case 102044:
                return "比赛Id为空";
            case 102045:
                return "当前赛事不能报名";
            case 102046:
                return "达到人数上限";
            case 102050:
                return "已经存在相同团队";
            case 102083:
                return "为查询到项目信息";
            case 102085:
                return "项目信息错误";
            case 102086:
                return "写入人数失败";
            case 102087:
                return "写入项目规则失败";
            case 102088:
                return "规则不正确";
            case 102089:
                return "修改成功";
            case 102090:
                return "修改失败";
            case 102091:
                return "出现人数未设置";
            case 102093:
                return "未获取到团队信息";
            case 102094:
                return "获取组织机构信息失败";
            case 102108:
                return "赛事id不合法";
            case 102109:
                return "不能删除该项目";
            case 102110:
                return "删除项目失败";


            case 103001:
//                return "未匹配到赛事项目";
                return "当前还未设置赛事项目信息";
            case 103002:
                return "未提供项目Id";
            case 103003:
//                return "未匹配到项目阶段详细信息";
                return "当前还未设置项目阶段信息";
            case 103004:
                return "阶段Id为空";
            case 103005:
                return "选手设置数据为空";
            case 103006:
                return "设置未空坑位数据为空";
            case 103007:
                return "未匹配到赛事Id";
            case 103008:
                return "当前赛事正在报名中耶";
            case 103009:
                return "赛事已经结束了哟";
            case 103010:
                return "正在报名为替补";
            case 103011:
                return "当前已经不能报名";
            case 103012:
                return "取消失败";
            case 103013:
                return "该项目目前没有比赛";
            case 103019:
                return "比赛已进行";


            case 104101:
                return "性别不符合";
            case 104102:
                return "真名未设置";
            case 104103:
                return "昵称未设置";
            case 104104:
                return "积分不足";
            case 104105:
                return "积分超出";
            case 104106:
                return "年龄过大";
            case 104107:
                return "年龄过小";


            case 105101:
                return "规则编码为空";


            case 106001:
                return "俱乐部Id为空";
            case 106002:
                return "没有俱乐部权限";
            case 106003:
                return "只有群主才可以取消管理员";
            case 106004:
                return "已经是管理员了";
            case 106005:
                return "该用户不是管理员";
            case 106006:
                return "要修改的档次标准不存在";
            case 106007:
                return "活动名称过短";
            case 106008:
                return "活动名称过长";
            case 106009:
                return "活动介绍过长";
            case 106010:
                return "活动介绍过短";
            case 106011:
                return "没有开始时间";
            case 106012:
                return "没有结束时间";
            case 106013:
                return "活动开始时间非法";
            case 106014:
                return "活动结束时间非法";
            case 106015:
                return "创建者获取权限失败";
            case 106016:
                return "活动不存在";
            case 106019:
                return "该用户不是俱乐部成员";
            case 106020:
                return "俱乐部存在改成员";
            case 106021:
                return "俱乐部存在改成员";
            case 106022:
                return "俱乐部邀请成员失败";
            case 106023:
                return "审核俱乐部申请失败";
            case 106024:
                return "俱乐部不存在该成员";
            case 106025:
                return "审核俱乐部成员失败";
            case 106026:
                return "俱乐部成员已审核";



            case 107001:
                return "已经报名该项目";
            case 107002:
                return "报名该项目失败";
            case 107003:
                return "未匹配到该项目信息";
            case 107005:
                return "已经报名成功";
            case 107006:
                return "已经报名待审核";
            case 107007:
                return "已经报名审核未通过";
            case 107008:
                return "用户信息写入失败";
            case 107009:
                return "用户未报名该赛事";
            case 107010:
                return "获取选手名单失败";
            case 107011:
                return "有选手不存在小组中";
            case 107012:
                return "团队成员存在";

            case 108101:
                return "没有权限";
            case 108102:
                return "当前已经设置了赛制";
            case 108103:
                return "赛事id未知";
            case 108104:
                return "缺少roundRobin数据提交";
            case 108105:
                return "未获取到eventSystem数据";
            case 108106:
                return "阶段详表Id为空";
            case 108107:
                return "阶段设置为空";
            case 108108:
                return "赛事已经设置";
            case 108109:
                return "缺少knockOutId";
            case 108110:
                return "缺少lotbookCol数据";
            case 108111:
                return "groupId为空";
            case 108112:
                return "坑位不存在";
            case 108113:
                return "赛事同一阶段同一用户仅能出现一次";
            case 108114:
                return "阶段已结束";
            case 108115:
                return "选手已存在groupNameList";
            case 108116:
                return "坑位已被占";
            case 108117:
                return "阶段未结束";
            case 108118:
                return "赛事管理员已满";
            case 108119:
                return "轮空数不正确";
            case 108121:
                return "自定义轮有重复";


            default:
                return "未知错误! >_<";
        }
    }

//    /**
//     * 注册相关返回码处理
//     *
//     * @param errorCode
//     * @return
//     */
//    public static String Register(String errorCode) {
//        int Code = Integer.parseInt(errorCode);
//        switch (Code) {
//
//
//        }
//        return "";
//    }
//
//    /**
//     * 设置个人卡片返回码处理
//     *
//     * @param errorCode
//     * @return
//     */
//    public static String SetCard(String errorCode) {
//        int Code = Integer.parseInt(errorCode);
//        switch (Code) {
//
//        }
//        return "";
//    }
//
//    /**
//     * 修改密码返回码处理
//     *
//     * @param errorCode
//     * @return
//     */
//    public static String ChangePass(String errorCode) {
//        int Code = Integer.parseInt(errorCode);
//        switch (Code) {
//
//        }
//        return "";
//    }
//
//    /**
//     * 其他返回码处理
//     *
//     * @param errorCode
//     * @return
//     */
//    public static String Other(String errorCode) {
//        int Code = Integer.parseInt(errorCode);
//        switch (Code) {
//
//        }
//        return "";
//    }
}
