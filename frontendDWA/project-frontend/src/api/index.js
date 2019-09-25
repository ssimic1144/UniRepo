import axios from "axios"

const API_URL = "http://127.0.0.1:5000"

export function auth(userData){
    return axios.post(`${API_URL}/login`, userData)
}