import moment from "moment";
moment.locale("zh-cn");

export default {
    filters: {
        formatToDate: function (value) {
            return moment(value).format("YYYY/MM/DD");
        },
        formatToDateTime: function (value) {
            return moment(value).format("YYYY/MM/DD HH:mm:ss");
        },
        formatToReadableTime: function (value) {
            return moment(value).fromNow();
        }
    }
}