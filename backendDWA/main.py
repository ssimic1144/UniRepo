from flask import Flask, Response, jsonify, request, current_app
import datetime as dt
from flask_cors import CORS
from domain import Poslodavci, Prakse
import jwt


app = Flask(__name__)

CORS(app)

def error(status = 500, text = "An error happend"):
    return jsonify({"error":text}), status

@app.route("/poslodavci", methods = ["POST"])
def poslodavac_dodaj():
    status, greske = Poslodavci.dodaj(request.get_json())
    if status:
        return Response(status=201)
    else:
        r = Response(status=500)
        r.set_data(greske)
        return r

@app.route("/poslodavci", methods = ["GET"])
def listaj_poslodavce():
    poslodavci = Poslodavci.listaj()
    return jsonify({"data":poslodavci})

@app.route("/login", methods =["POST"])
def login():
    data = request.get_json()
    user = Poslodavci.auth(**data)
    if not user:
        return jsonify({"msg":"Invalid credentials"}), 401
    #trebao bi se uvijek sastojati samo od jednog dict(lista user)
    userEmail = user[0]["email"]
    token = jwt.encode({
        "sub":userEmail,
        "iat":str(dt.datetime.now()),
        "exp": str(dt.datetime.now() + dt.timedelta(minutes=30))
    }, str(current_app.config["SECRET_KEY"]))

    return jsonify({"token": token.decode("UTF-8")})


@app.route("/poslodavci/<idPos>", methods = ["GET"])
def jedan_poslodavac(idPos):
    poslo = Poslodavci.jedanPoslodav(idPos)
    return jsonify({"data":poslo})

@app.route("/poslodavci/<email>", methods = ["GET"])
def prakse_poslodavca(email):
    print("Dohvacanje je pocelo")
    poslo = Poslodavci.praksePoslo(email)
    print(poslo)
    return jsonify({"data":poslo})

@app.route("/prakse", methods = ["GET"])
def listaj_prakse():
    prakse = Prakse.listaj()
    return jsonify({"data":prakse})

@app.route("/prakse", methods = ["POST"])
def dodaj_prakse():
    status, greske = Prakse.dodaj(request.get_json())
    if status:
        return Response(status=201)
    else:
        r = Response(status=500)
        r.set_data(greske)
        return r

@app.route("/prakse/<prakID>/", methods= ["DELETE"])
def izbrisi_praksu(prakID):
    print(prakID)
    response = Prakse.izbrisi(prakID)
    if response is None:
        return error()
    return Response(status=202)

if __name__ == '__main__':
    app.debug = True
    app.run(port=5000)
