import axios from 'axios';

const API_URL = "http://localhost:8080/api/v1";

class TicketService {

    createTicket(token, ticket) {
        return axios.post(API_URL + "/createTicket", ticket, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Basic ${token}`
            }
        })
    }

    getTicketList(token) {
        return axios.get(API_URL + '/' + 'getTicketList', {
            headers: {
                'Authorization': `Basic ${token}`
            }
        });
    }

    getTicket(token, ticketId) {
        return axios.get(API_URL + "/getTicketById" + "/" + ticketId,{
            headers: {
                'Authorization': `Basic ${token}`
            }
        });
    }

    getForm(token, ticketId, formId) {
        return axios.get(API_URL + "/ticket" + "/" + ticketId + "/getForm" + "/" + formId,{
            headers: {
                'Authorization': `Basic ${token}`
            }
        });
    }

    storeForm(token, ticketId, formId, formData) {
        return axios.post(API_URL + "/ticket" + "/" + ticketId + "/storeForm" + "/" + formId, formData, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Basic ${token}`
            }
        })
    }

    postComment(token, ticketId, comment) {
        return axios.post(API_URL + "/postComment" + "/" + ticketId, comment, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Basic ${token}`
            }
        })
    }

    ticketDecision(token, ticketId, decision) {
        return axios.post(API_URL + "/ticketDecision" + "/" + ticketId, decision, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Basic ${token}`
            }
        })
    }

}

export default new TicketService()