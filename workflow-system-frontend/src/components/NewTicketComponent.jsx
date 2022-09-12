import formJSON from '../formElement.json';
import { render } from '@testing-library/react';

import React, { Component } from 'react'
import Navbar from '../components/navbar/Navbar';


class NewTicketComponent extends Component {


    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
            elements: {},
            lastName: '',
            emailId: ''
        }

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.Checkbox = this.Checkbox.bind(this);
        this.Input = this.Input.bind(this);
        this.Select = this.Select.bind(this);
        this.Element = this.Element.bind(this);
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



    componentDidMount(){
        this.setState({elements: formJSON[0]});
        
    }
    
  handleSubmit = (event) => {
    event.preventDefault();

    console.log(this.state.elements)
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
        <div className="App container">
          <h3>{this.state.elements.page_label}</h3>
          <form>
            {this.state.elements.fields ? this.state.elements.fields.map((field, i) => <this.Element key={i} field={field} />) : null}
            <button type="submit" className="btn btn-primary" onClick={(e) => this.handleSubmit(e)}>Submit</button>
          </form>
  
        </div>
        </div>
      )
  }


}

export default NewTicketComponent