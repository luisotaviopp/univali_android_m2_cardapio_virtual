from email import header
from wsgiref import headers
from flask import Flask, request, jsonify, make_response
from flask.helpers import send_file
from flask_cors import CORS
from werkzeug.security import check_password_hash, generate_password_hash
import mysql.connector

db_host = 'localhost'
db_user = 'luis'
db_password = 'mudar_quando_executar' #trocar esses dados depois
db_database = 'restaurant_menu'
db_charset = 'utf8'

app = Flask(__name__)
CORS(app)

app.config['SECRET_KEY'] = 'ThisIsTheSecretKey@'

# -----------------------------------------------------> Hey you... Out there in the cold...
@app.route("/")
def hello_world():
    response = make_response("Hey you!")
    response.headers.add('Access-Control-Allow-Origin', '*')
    return response


# -----------------------------------------------------> Lista todos os produtos
@app.route("/product")
def get_products():

    # Inicia SQL
    connection = mysql.connector.connect(host=db_host, user=db_user, password=db_password, database=db_database, charset=db_charset, auth_plugin='mysql_native_password')
    cursor = connection.cursor(dictionary=True)

    # Executa a Query
    cursor.execute("SELECT p.name as product_name, p.amount, p.description, c.name as category_name \
                    FROM product p \
                    INNER JOIN category c \
                    ON p.id = c.id;")

   # Pega os dados retornados.
    products = cursor.fetchall()

    # Cria o dicionário que será serialzado em Json
    output = []

    for product in products:
        product_data = {}
        product_data['product_name'] =  product['product_name']
        product_data['amount'] =  product['amount']
        product_data['description'] =  product['description']
        product_data['category_name'] = product['category_name']
        output.append(product_data)

    # Fecha a conexão do sql
    connection.close()

     # Cast para json
    response = jsonify(output)
    response.headers.add('Access-Control-Allow-Origin', '*')

    # Adios amigos.
    return response



# -----------------------------------------------------> Lista todos os produtos de uma categoria
@app.route("/product/category/<cat_id>")
def get_products_by_category_id(cat_id):

    # Inicia SQL
    connection = mysql.connector.connect(host=db_host, user=db_user, password=db_password, database=db_database, charset=db_charset, auth_plugin='mysql_native_password')
    cursor = connection.cursor(dictionary=True)

    # Executa a Query
    cursor.execute("SELECT p.name as product_name, p.amount, p.description, p.thumb_url, c.name as category_name \
                    FROM product p \
                    INNER JOIN category c \
                    ON p.id = c.id\
                    WHERE c.id = {};".format(cat_id))

    # Pega os dados retornados.
    products = cursor.fetchall()

    # Cria o dicionário que será serialzado em Json
    output = []

    for product in products:
        product_data = {}
        product_data['product_name'] =  product['product_name']
        product_data['amount'] =  product['amount']
        product_data['description'] =  product['description']
        product_data['thumb_url'] =  product['thumb_url']
        product_data['category_name'] = product['category_name']
        output.append(product_data)

    # Fecha a conexão do sql
    connection.close()

    # Cast para json
    response = jsonify(output)
    response.headers.add('Access-Control-Allow-Origin', '*')

    # Adios amigos.
    return response