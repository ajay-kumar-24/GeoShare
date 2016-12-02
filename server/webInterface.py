from flask import Flask,request
import signin
import post
import get_nn
from flask import jsonify
import getfriends

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
        temp = get_nn.get_nn_func(lat,lon)
        print temp
        return jsonify(results=temp)


@app.route('/addfriends', methods=['GET'])
def add():
    print 'hello'
    if request.method == 'GET':
        req = request.args
        user = req['user']
        s = getfriends.add_friends()
        print 'HI'
        print s
        names = []
        fri = []
        fr = getfriends.get_friend_id(user)
        print 'TTT'
        print fr
        print fr[0][0]

        fri.append(int(fr[0][0]))
        fri.append(int(user))
        for i in s:
            if int(i[0]) in fri:
                continue
            e = getfriends.get_name(i[0])

            print e[0][0]

            names.append(e[0][0])
        s = ','.join(names)
        print s
        return s

@app.route('/getfriends', methods=['GET'])
def get_friends():
    if request.method == 'GET':
        req = request.args
        user = req['user']
        s = getfriends.get_friend_id(user)
        print s
        names = []
        for i in s:
            print i[0]
            e = getfriends.get_name(i[0])
            print e
            print e[0][0]

            names.append(e[0][0])
        s = ','.join(names)
        print s
        return s


if __name__ == '__main__':
	app.run(host = '0.0.0.0',port=8080)
