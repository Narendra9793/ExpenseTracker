FROM python:3.14.3

WORKDIR /app

COPY dist/dsservice-1.0.tar.gz .

RUN pip install --no-cache-dir dsservice-1.0.tar.gz

ENV FLASK_APP=app.main:app

EXPOSE 7070

CMD ["flask", "run", "--host=0.0.0.0", "--port=7070"]