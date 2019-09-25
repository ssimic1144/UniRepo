from pony.orm import Database, PrimaryKey, Required, Set, db_session, Optional
from uuid import uuid4 
import datetime as dt
import os

db = Database()

db.bind(provider = "sqlite", filename = "database.sqlite", create_db=True)    

class Poslodavac(db.Entity):
    _table_ = "poslodavac"
    id = PrimaryKey(str, default = lambda: str(uuid4()))
    ime = Required(str)
    email = Required(str, unique=True)
    pasw = Required(str)
    prakse = Set("Praksa")

class Praksa(db.Entity):
    _table_ = "prakse"
    id = PrimaryKey(str, default = lambda: str(uuid4()))
    ime = Required(str)
    podrucjePrakse = Required(str)
    created_at = Required(dt.datetime, default = lambda: dt.datetime.now())
    updated_at = Required(dt.datetime, default = lambda: dt.datetime.now())
    brojStudenta = Required(int)
    poslodavac = Required(Poslodavac)

db.generate_mapping(check_tables=True, create_tables=True)

