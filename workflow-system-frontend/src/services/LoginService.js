import axios from 'axios';

const LOGIN_API_LOGIN_URL = "http://localhost:8080/api/v1/login";

class LoginService {

    login(login) {
        return axios.post(LOGIN_API_LOGIN_URL, login)
    }

}

export default new LoginService()