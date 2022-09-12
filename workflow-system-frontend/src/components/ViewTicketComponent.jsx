import formJSON from '../formElement.json';
import { render } from '@testing-library/react';

import React, { Component } from 'react'
import Navbar from './navbar/Navbar';
import TicketService from '../services/TicketService';
import { getToken } from '../utils/common';


class ViewTicketComponent extends Component {

    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
            ticket: {
                formInfoList: [],
                decisionList: [],
                commentList: [],
                ticketFlowList: []
            },
            formInfo: [],
            comment: ''
        }
        this.viewForm = this.viewForm.bind(this);
        this.editForm = this.editForm.bind(this);
        this.back = this.back.bind(this);
        this.decisionButton = this.decisionButton.bind(this);
        this.addComment = this.addComment.bind(this);
    }

    componentDidMount(){
        const token = getToken();
        TicketService.getTicket(token, this.state.id).then( res => {
            this.setState({ticket: res.data});
        })
        
    }

    back(){
        this.props.history.push(`/listTicket`);
    }

    viewForm(formid){
        this.props.history.push(`/ticket/${this.state.id}/viewForm/${formid}`);
    }

    editForm(formid){
        this.props.history.push(`/ticket/${this.state.id}/editForm/${formid}`);
    }

    decisionButton(decision){
        const token = getToken();
        TicketService.ticketDecision(token, this.state.id, decision).then( res => {
        })
        this.props.history.push(`/listTicket`);
    }

    addComment= (event) => {
        this.setState({comment: event.target.value});
    }

    postComment = (e) => {
        e.preventDefault();
        const token = getToken();
        TicketService.postComment(token, this.state.id, this.state.comment).then( res => {
            this.setState({comment: ''});
        });
        TicketService.getTicket(token, this.state.id).then( res => {
            this.setState({ticket: res.data});
        })
    }


    render() {
        return (
            <div>
                <Navbar />
                <div className="container">
                    <h2 className="text-center">Ticket Information</h2>
                    <br></br>
                    <div className = "row">
                            <table className = "table table-striped table-bordered">

                                <thead>
                                    <tr>
                                        <th> Title</th>
                                        <th> Current Status</th>
                                        <th> Created By</th>
                                        <th> Currently At</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr key = {this.state.ticket.id}>
                                        <td> { this.state.ticket.ticketTitle} </td>   
                                        <td> {this.state.ticket.currentStatus}</td>
                                        <td> {this.state.ticket.createdBy}</td>
                                        <td> {this.state.ticket.currentPerson}</td>
                                    </tr>
                                </tbody>
                            </table>

                    </div>
                </div>
                <div className="container">
                    <h2 className="text-center">Form List</h2>
                    <br></br>
                    <div className = "row">
                            <table className = "table table-striped table-bordered">

                                <thead>
                                    <tr>
                                        <th> Form Name</th>
                                        <th> Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        this.state.ticket.formInfoList.map(
                                            temp => 
                                            <tr key = {temp.id}>
                                                <td> { temp.name} </td>
                                                {temp.access === 'view' &&
                                                    <td>
                                                        <button onClick={ () => this.viewForm(temp.id)} className="btn btn-info">View </button>
                                                    </td>
                                                } {temp.access === 'edit' &&
                                                    <td>
                                                        <button onClick={ () => this.editForm(temp.id)} className="btn btn-info">Edit </button>
                                                    </td>
                                                }
                                                
                                            </tr>
                                        )
                                    }
                                </tbody>
                            </table>

                    </div>
                </div>

                <div className="container">
                    <h2 className="text-center">Ticket Flow Path</h2>
                    <br></br>
                    <div className = "row">
                            <table className = "table table-striped table-bordered">

                                <thead>
                                    <tr>
                                        <th> Path</th>
                                        <th> Date</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        this.state.ticket.ticketFlowList.map(
                                            temp => 
                                            <tr key = {temp.id}>
                                                <td> { temp.path} </td>
                                                <td> { temp.date} </td>
                                            </tr>
                                        )
                                    }
                                </tbody>
                            </table>
                    </div>
                </div>

                <div className="container">
                    <h2 className="text-center">Comment</h2>
                    <br></br>
                    <div className = "row">
                            <table className = "table table-striped table-bordered">

                                <thead>
                                    <tr>
                                        <th> User</th>
                                        <th> Comment</th>
                                        <th> Date</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        this.state.ticket.commentList.map(
                                            temp => 
                                            <tr key = {temp.id}>
                                                <td> { temp.userName} </td>
                                                <td> { temp.message} </td>
                                                <td> { temp.date} </td>
                                            </tr>
                                        )
                                    }
                                </tbody>
                            </table>
                    </div>
                </div>

                <div className = "container">
                        <div className = "row">
                            <div className = "table table-striped table-bordered">
                                <h3 className="text-center">Your Comment</h3>
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> Comment: </label>
                                            <input placeholder="Your Comment" name="comment" className="form-control" 
                                                value = {this.state.comment} onChange={this.addComment} ref="commentInput"/>
                                        </div>

                                        <button className="btn btn-success" onClick={this.postComment}>Add Comment</button>
                                    </form>
                                </div>
                            </div>
                        </div>

                   </div>

                <div className="container">
                    <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> Decision</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                        {
                                            this.state.ticket.decisionList.map(
                                                temp => 
                                                        <button style={{marginLeft: "10px"}} onClick={ () => this.decisionButton(temp.name)} className="btn btn-info">{temp.name} </button>
                                                
                                            )
                                        }
                                        <button style={{marginLeft: "10px"}} className="btn btn-info" onClick={this.back}>Back</button>
                                    </td>
                                            
                                </tr>
                            </tbody>
                        </table>

                    </div>
                    
                </div>

            </div>
        )
    }


}

export default ViewTicketComponent
