from flask import Flask,request
import signin
import post
import get_nn
from flask import jsonify

app = Flask(__name__)

@app.route('/signin', methods=['GET'])
def test():
    if request.method == 'GET':
        req  = request.args
        user = req['user']
        password = req['pass']
        print user,password
        return signin.sign_in(user,password)

@app.route('/post_status', methods=['GET'])
def post_add():
    if request.method == 'GET':
        req  = request.args
        lat = req['lat']
        lon = req['lon']
        post_text = req['post']
        user_id = req['user']
        return post.add_posts(user_id,post_text,lat,lon)

@app.route('/get_nn', methods=['GET'])
def send_values():
    if request.method == 'GET':
        req  = request.args
        lat = req['lat']
        lon = req['lon']
        user_id = req['user']
        temp = get_nn.get_nn_func(user_id,lat,lon)
        print temp
        return jsonify(results=temp)

if __name__ == '__main__':
	app.run(host = '0.0.0.0',port=8080)
