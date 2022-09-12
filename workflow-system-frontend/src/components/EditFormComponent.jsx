import formJSON from '../formElement.json';
import { render } from '@testing-library/react';

import React, { Component } from 'react'
import Navbar from '../components/navbar/Navbar';
import TicketService from '../services/TicketService';
import { getToken } from '../utils/common';


class EditFormComponent extends Component {

    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
            formid: this.props.match.params.formid,
            elements: {},
            dummy: ''
        }
        this.handleSubmit = this.handleSubmit.bind(this);
        this.Checkbox = this.Checkbox.bind(this);
        this.Input = this.Input.bind(this);
        this.Select = this.Select.bind(this);
        this.Element = this.Element.bind(this);
        this.back = this.back.bind(this);
    }

    componentDidMount(){
        const token = getToken();
        TicketService.getForm(token, this.state.id, this.state.formid).then( res => {
            let responseData = res.data;
            this.state.elements.name = responseData.form.formName;
            this.state.elements.fields = JSON.parse(responseData.filledFormMetadata);
            console.log(this.state.elements);
            console.log(this.state.elements.fields);
            this.setState({dummy : 'dummy'});
        })
        
    }

    back(){
        this.props.history.push(`/viewTicket/${this.state.id}`);
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
            </div>
        )
    }

    Select = ({ fieldId, fieldLabel, fieldPlaceholder, fieldValue, fieldOptions }) => {
        
        return (
            <>
                <label className="form-label">{fieldLabel}</label>
                <select className="form-control"
                    onChange={event => this.handleChange(fieldId, event)}
                >
                    {fieldValue === null && <option >Open this select menu</option>}
                    {fieldValue !== null && <option >{fieldValue}</option>}
                    
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
    
  handleSubmit = (event) => {
    event.preventDefault();
    const token = getToken();
    TicketService.storeForm(token, this.state.id, this.state.formid, this.state.elements)
    this.props.history.push(`/viewTicket/${this.state.id}`);
  }

  handleChange = (id, event) => {
    const newElements = { ...this.state.elements }
    newElements.fields.forEach(field => {
      const { fieldType, fieldId } = field;
      if (id === fieldId) {
        switch (fieldType) {
          case 'checkbox':
            field['fieldValue'] = event.target.checked;
            break;

          default:
            field['fieldValue'] = event.target.value;
            break;
        }


      }
      this.setState({elements: newElements});
    });
    console.log(this.state.elements)
  }

  render() {
      return (
        <div>
        <Navbar />
        <div className = "container">
            <div className = "row">
                <div className = "card col-md-6 offset-md-3 offset-md-3">
                    <h3 className="text-center">{this.state.elements.name}</h3>
                    <div className = "card-body">
                        <form>
                            {this.state.elements.fields ? this.state.elements.fields.map((field, i) => <this.Element key={i} field={field} />) : null}
                            <div className = "row">
                                
                                <button type="submit" className="btn btn-primary" onClick={(e) => this.handleSubmit(e)}>Save</button>
                                <button className="btn btn-primary" onClick={this.back}>Back</button>
                            </div>
                        </form>
                    </div>
                </div>  
            </div>
          </div>
        </div>
      )
  }



}

export default EditFormComponent
