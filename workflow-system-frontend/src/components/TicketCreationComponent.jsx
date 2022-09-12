import formJSON from '../formElement.json';
import { render } from '@testing-library/react';

import React, { Component } from 'react'
import WorkflowService from '../services/WorkflowService'
import TicketService from '../services/TicketService';
import Navbar from '../components/navbar/Navbar';
import { getToken,getWorkflowName } from '../utils/common';
import {Circles} from 'react-loader-spinner';



class TicketCreationComponent extends Component {


    constructor(props) {
        super(props)

        this.state = {
            // step 2
            loading: false,
            id: this.props.match.params.id,
            elements: {},
            data: {},
            formData: {},
            usersInWorkflow: {},
            fileNames: {},
            workflowPath: ''
        }

        this.handleChange = this.handleChange.bind(this);
        this.createTicket = this.createTicket.bind(this);
        this.usersInWorkflow = this.usersInWorkflow.bind(this);
        this.Checkbox = this.Checkbox.bind(this);
        this.Input = this.Input.bind(this);
        this.Select = this.Select.bind(this);
        this.Element = this.Element.bind(this);
        this.handleWorkflowPathChange = this.handleWorkflowPathChange.bind(this);
    }

    componentDidMount(){
        const token = getToken();
        const workflowName = getWorkflowName();
        this.state.formData.workflowName = workflowName;
        WorkflowService.getWorkflowUsers(token, workflowName).then( res => {
            this.setState({data: res.data});
            console.log(this.state.data.student);
            console.log(this.state.data.asdf);
            Object.keys(this.state.data).map((key, i) => console.log("key name: " + key + "value : " + this.state.data[key]));
        })
        
    }

    handleWorkflowPathChange(event) {
        this.setState({workflowPath: event.target.value});
    }

    Checkbox = ({ fieldId, fieldLabel, fieldValue }) => {
        
        return (
            <div className="mb-3 form-check">
                <input type="checkbox" className="form-check-input" id="exampleCheck1" checked={fieldValue}
                    onChange={event => this.handleChange(fieldId, event)}
                />
                <label className="form-check-label" htmlFor="exampleCheck1">{fieldLabel}</label>
            </div>
        )
    }

    Input = ({ fieldId, fieldLabel, fieldPlaceholder, fieldValue }) => {
        return (
            <div className="mb-3">
                <label htmlFor="exampleInputEmail1" className="form-label">{fieldLabel}</label>
                <input type="text" className="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"
                    placeholder={fieldPlaceholder ? fieldPlaceholder : ''}
                    value={fieldValue}
                    onChange={event => this.handleChange(fieldId, event)}
                />
                <div id="emailHelp" className="form-text">We'll never share your email with anyone else.</div>
            </div>
        )
    }

    Select = ({ fieldId, fieldLabel, fieldPlaceholder, fieldValue, fieldOptions }) => {
        
        return (
            <>
                <label className="form-label">{fieldLabel}</label>
                <select className="form-select" aria-label="Default select example"
                    onChange={event => this.handleChange(fieldId, event)}
                >
                    <option >Open this select menu</option>
                    {fieldOptions.length > 0 && fieldOptions.map((option, i) =>
                        <option value={option.optionLabel} key={i}>{option.optionLabel}</option>
    
                    )}
                </select>
            </>
        )
    }

    Element = ({ field: { fieldType, fieldId, fieldLabel, fieldPlaceholder, fieldValue, fieldOptions } }) => {

        switch (fieldType) {
            case 'text':
                return (<this.Input
                    fieldId={fieldId}
                    fieldLabel={fieldLabel}
                    fieldPlaceholder={fieldPlaceholder}
                    fieldValue={fieldValue}
    
                />)
            case 'select':
                return (<this.Select
                    fieldId={fieldId}
                    fieldLabel={fieldLabel}
                    fieldPlaceholder={fieldPlaceholder}
                    fieldValue={fieldValue}
                    fieldOptions={fieldOptions}
                />)
            case 'checkbox':
                return (<this.Checkbox
                    fieldId={fieldId}
                    fieldLabel={fieldLabel}
                    fieldValue={fieldValue}
                />)
    
            default:
                return null;
        }
    }

    createTicket = (event) => {
        event.preventDefault();
        this.setState({
            loading: true
          });
        const token = getToken();
        console.log(this.state.formData)
        this.state.formData['userInDifferentStage'] = this.state.usersInWorkflow;
        TicketService.createTicket(token, this.state.formData).then( res => {
            this.setState({
                loading: false
                
              });
            if(this.state.workflowPath === 'Hybrid') {
                this.props.history.push(`/hybridPath/${res.data.ticketId}`);
            } else {
                this.props.history.push('/dashboard');
            }
          });
    }

  handleChange = (id, event) => {

    const newFormData = { ...this.state.formData }
    newFormData[id] = event.target.value;
    this.setState({formData: newFormData})
    console.log(this.state.formData)
  }

  usersInWorkflow = (id, event) => {
      const newUsersInWorkflow = { ...this.state.usersInWorkflow }
      newUsersInWorkflow[id] = event.target.value;
      this.setState({usersInWorkflow : newUsersInWorkflow})
      console.log('userinworkflow')
      console.log(this.state.usersInWorkflow)
  }

  render() {

    if(this.state.loading){
        return ( 
          <div style={{display: 'flex',  justifyContent:'center', alignItems:'center', height: '100vh'}}>
            <Circles color="#00BFFF" height={80} width={80}/>
          </div>
         );
      }
      else {

      return (
        <div>
            <Navbar />
            <div>
                <br></br>
                <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                <h3 className="text-center">Create Ticket</h3>
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <div className="mb-3">
                                                <label htmlFor="exampleInputEmail1" className="form-label">Ticket Name</label>
                                                <input type="text" className="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"
                                                    placeholder={'Enter the title of the ticket'}
                                                    onChange={event => this.handleChange('ticketTitle', event)}
                                                />
                                            </div>

                                            {

                                                Object.keys(this.state.data).map((key, i) => (

                                                <div>
                                                    <p key={i}>

                                                    </p><label className="form-label">{key}</label><select className="form-control" 
                                                        onChange={event => this.usersInWorkflow(key, event)}
                                                    >
                                                            <option>Open this select menu</option>
                                                            {this.state.data[key].length > 0 && this.state.data[key].map((user, i) => <option value={user} key={i}>{user}</option>

                                                            )}
                                                        </select>
                                                </div>

                                                ))

                                            }
                                            <div >
                                                <label className="form-label"> Choose Workflow Path </label>
                                                <select className="form-control" 
                                                    onChange={this.handleWorkflowPathChange}>
                                                    <option >Open this select menu</option>
                                                    <option value='Hybrid'>Hybrid</option>
                                                    <option value='Normal'>Normal</option>
                                                </select>
                                            </div>
                                        </div>
                                        <button className="btn btn-success" onClick={this.createTicket}>Create</button>
                                    </form>
                                </div>
                            </div>
                        </div>

                </div>
            </div>
        </div>

        
      )
    }
  }


}

export default TicketCreationComponent