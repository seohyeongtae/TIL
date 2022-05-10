
// node js 는 자바스크립트만 알면 클라이언트, 서버 모두 개발이 가능하다.
// npm을 통해서 이미 잘 개발되어진 모듈을 빠르게 설치, 적용할 수 있다. (오픈소스)


/* 이메일 발송하는 프로그램 */
// npm  nodemailer  사용
// npm install nodemailer


const nodemailer = require('nodemailer');

// https://mailtrap.io/ 
// 에서 테스트 가능하다
const email = {
    "host" : "",
    "port" : "",
    "secure" : false,
    "auth" : {
        "user" : "",
        "pass" : ""
    }
};
const send = async (option) => {
    nodemailer.createTransport(email).sendMail(option, (error, info) => {
        if(error) {
            console.log(error);
        } else {
            console.log(info);
            return info.response;
        }
    });
};

let email_data = {
    from : '42.4.hyseo@gmail.com',
    to : '42.4.hyseo@gmail.com',
    subject : 'test mail 입니다.',
    test : 'nodejs 한시간 강의'
}

send(email_data);