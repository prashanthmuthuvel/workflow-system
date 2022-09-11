FROM python:3.7

WORKDIR /opt/app

COPY . .

RUN apt-get update
RUN apt-get install ffmpeg libsm6 libxext6  -y

RUN pip install --no-cache-dir -r requirements-prod.txt

RUN apt-get install -y poppler-utils

RUN apt-get install -y tesseract-ocr

EXPOSE 5000

CMD ["python3", "-m", "flask", "run", "--host=0.0.0.0"]
