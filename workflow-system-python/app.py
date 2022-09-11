from flask import Flask, request
from SignatureRecognition import SignatureRecognition
import json

app = Flask(__name__)


@app.route('/')
def home():
    return "App Works!!!"

@app.route('/handle_form', methods=['POST'])
def handle_form():
    print("Posted file: {}".format(request.files['file']))
    file = request.files['file']
    file.save('save.pdf')
    text = request.form['textToBeFound']
    text_array = text.split('#')
    text_to_be_found = {}
    for value in text_array:
        text_to_be_found[value] = 'Not Found'
    object = SignatureRecognition('save.pdf', text_to_be_found)
    result = object.process()
    return result
