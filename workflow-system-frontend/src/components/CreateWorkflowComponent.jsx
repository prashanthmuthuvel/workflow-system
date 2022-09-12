import React, { Component } from 'react'
import WorkflowService from '../services/WorkflowService'
import ReactDOM from 'react-dom';
import Navbar from '../components/navbar/Navbar';
import HeaderComponent from '../components/HeaderComponent';
import { getToken,getUser, getIsAdmin, removeUserSession, setUserSession } from '../utils/common';

class CreateWorkflowComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            userName: '',
            textAreaValue: ''
        }
        this.handleChange = this.handleChange.bind(this);
        this.createWorkflow = this.createWorkflow.bind(this);
    }

    handleChange(event) {
        this.setState({ textAreaValue: event.target.value });
    }

    createWorkflow = (e) => {
        e.preventDefault();
        const token = getToken();
        let workflow = this.state.textAreaValue;
        WorkflowService.createWorkflow(token, workflow).then( res => {
            this.props.history.push('/dashboard');
        });
    }

    componentDidMount(){
    }

    render() {
        return (
            <div>
                <Navbar />
                <div>
                    <br></br>
                    <div className = "container">
                            <div className = "row">
                                <div className = "card col-md-6 offset-md-3 offset-md-3">
                                    <h3 className="text-center">Workflow</h3>
                                    <div className = "card-body">
                                        <form>
                                            <div className = "form-group">
                                                <label> Enter Workflow Schema: </label>
                                                <textarea className="form-control" value={this.state.textAreaValue} onChange={this.handleChange}
                                                    rows={15} cols={10}/>
                                            </div>
                                            <button className="btn btn-success" onClick={this.createWorkflow}>Create Workflow</button>
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

export default CreateWorkflowComponent
