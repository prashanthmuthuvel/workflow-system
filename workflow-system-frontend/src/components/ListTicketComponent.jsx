import formJSON from '../formElement.json';
import { render } from '@testing-library/react';

import React, { Component } from 'react'
import Navbar from '../components/navbar/Navbar';
import TicketService from '../services/TicketService';
import { getToken } from '../utils/common';


class ListTicketComponent extends Component {

    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
            tickets: []
        }
        this.viewTicket = this.viewTicket.bind(this);
        this.createTicket = this.createTicket.bind(this);
    }

    viewTicket(id){
        this.props.history.push(`/viewTicket/${id}`);
    }

    createTicket(){
        this.props.history.push('/chooseWorkflow');
    }

    componentDidMount(){
        const token = getToken();
        TicketService.getTicketList(token).then( res => {
            this.setState({ tickets: res.data });
            console.log(this.state.tickets);
        })
        console.log('componentdidmount');
        
    }


    render() {
        return (
            <div>
                <Navbar />
                <div className="container">
                    <h2 className="text-center">Ticket List</h2>
                    <div className = "row">
                        <button className="btn btn-primary" onClick={this.createTicket}>Create Ticket</button>
                    </div>
                    <br></br>
                    <div className = "row">
                            <table className = "table table-striped table-bordered">

                                <thead>
                                    <tr>
                                        <th> Title</th>
                                        <th> Current Status</th>
                                        <th> Created By</th>
                                        <th> Currently At</th>
                                        <th> Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        this.state.tickets.map(
                                            ticket => 
                                            <tr key = {ticket.id}>
                                                <td> { ticket.ticketTitle} </td>   
                                                <td> {ticket.currentStatus}</td>
                                                <td> {ticket.createdBy.firstName}</td>
                                                <td> {!!ticket.currentPerson ? ticket.currentPerson.firstName : ''}</td>
                                                <td>
                                                    <button onClick={ () => this.viewTicket(ticket.id)} className="btn btn-info">View </button>
                                                </td>
                                            </tr>
                                        )
                                    }
                                </tbody>
                            </table>

                    </div>
                </div>

            </div>
        )
    }


}

export default ListTicketComponent
