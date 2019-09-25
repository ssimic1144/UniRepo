from model import Poslodavac, Praksa
from pony.orm import db_session, select
from uuid import uuid4

class Poslodavci():
    @db_session()
    def dodaj(p):
        try:
            p["id"] = str(uuid4())
            p = Poslodavac(**p)
            return True, None
        except Exception as e:
            return False, str(e)
    @db_session()
    def listaj():
        try:
            querry = select(p for p in Poslodavac)
            return [x.to_dict() for x in querry]
        except Exception as e:
            return False, str(e)
        
    @db_session()
    def praksePoslo(email):
        try:
            querry = select(p for p in Poslodavac if p.email in email)
            data= [x.to_dict() for x in querry]
            prepData = data[0]["prakse"]
            print("This is prep data\n",prepData)
            return prepData
        except Exception as e:
            return False,str(e)

    @db_session()
    def jedanPoslodav(idPos):
        try:
            querry = select(p for p in Poslodavac if p.id in idPos)
            data = [x.to_dict() for x in querry]
            return data
        except Exception as e:
            return False, str(e)
    @db_session()
    def auth(**kwargs):
        try:
            querry = select(p for p in Poslodavac if (p.email == kwargs.get("email")) and (p.pasw == kwargs.get("pasw")))
            data = [x.to_dict() for x in querry]
            return data
        except Exception as e:
            return False, str(e) 

class Prakse():
    @db_session()
    def dodaj(prak):
        try:
            prak["id"] = str(uuid4())
            #salje se email iz kojega se selecta Id poslodavca koji se
            #upisuje
            print(prak["poslodavac"])
            querry = select(pos for pos in Poslodavac if pos.email == prak["poslodavac"])
            data = [x.to_dict() for x in querry]
            posloId = data[0]["id"]

            prak["poslodavac"] = posloId 
            
            prak = Praksa(**prak)
            return True, None
        except Exception as e:
            return False, str(e)
    @db_session()
    def listaj():
        try:
            querry = select(p for p in Praksa)
            print([x.to_dict() for x in querry])
            return [x.to_dict() for x in querry]
        except Exception as e:
            return False, str(e)
    @db_session()
    def izbrisi(idPrak):
        try:
            Praksa[idPrak].delete()
            return True, None
        except Exception as e:
            return False, str(e)