    DROP TABLE IF EXISTS wunschliste;
    DROP TABLE IF EXISTS wunschlistenersteller;
    DROP TABLE IF EXISTS design;
    DROP TABLE IF EXISTS wunsch;
    DROP TABLE IF EXISTS geschenk;
    
   /*
   ?????????????????????????????????????????
   DROP DOMAIN IF EXISTS      ;  -> vorgegebene eingaben definieren
      CREATE DOMAIN ..... AS ....;   -> inhaltliche eingaben
   ?????????????????????????????????????????
   */
   
   CREATE TABLE wunschliste(
       id_wunschliste           integer PRIMARY KEY ,
       name_wunschliste         text,
       ersteller_id             integer ,
       anlass                   text,
       ablaufdatum              date,
       listenpassword           text,
       design_id                integer,
       uberraschungsmodus       boolean NOT NULL ,
       
       FOREIGN KEY (name_ersteller) REFERENCES wunschlistenersteller (name) ,
       FOREIGN KEY (ersteller_id) REFERENCES wunschlistenersteller (id),
       FOREIGN KEY (design_id) REFERENCES desing (id)
      /*-> verbindung zwischen den tabellen herstellen*/
   );
   
  /* INSERT INTO wunschliste (ersteller_id,name_wunschliste,id_wunschliste,anlass,ablaufdatum,listenpassword,design_id,uberraschungsmodus)
           VALUES
           ()
           */
           
      CREATE TABLE wunschlistenersteller(
          name           text,
          e_mail         text,
          id             integer PRIMARY KEY,
          
          FOREIGN KEY (name)REFERENCES wunschliste(name_ersteller),
          FOREIGN KEY (id)REFERENCES wunschliste(ersteller_id)
      );
      
      /* INSERT INTO wunschlistenersteller (name,e_mail,id)
           VALUES
           ()
           */
           
      CREATE TABLE design(
          id               integer PRIMARY KEY,
          hintergrundfarbe text,
          schriftfarbe     text,
          schriftart       text,
          
          FOREIGN KEY (id) REFERENCES wunschliste(design_id)
      );
      
      /* INSERT INTO design (id,hintergrundfarbe,schriftfarbe,schriftart)
           VALUES
           ()
           */
           
      CREATE TABLE wunsch(
          id             integer PRIMARY KEY,
          name           text,
          id_wunschliste integer,
          beschreibung   text,
          link_wunsch    text,
          
          FOREIGN KEY (id) REFERENCES wunschliste (id_wunsch),
          FOREIGN KEY (name) REFERENCES wunschliste (name_wunsch),
          FOREIGN KEY (id_wunschliste) REFERENCES wunschliste(id_wunschliste)
          
      );
      
      /* INSERT INTO wunsch (name,id,id_wunschliste,beschreibung,link_wunsch)
           VALUES
           ()
           */
           
      CREATE TABLE geschenk(
          id_wunsch     integer PRIMARY KEY,
          name_schenker text,
          
          FOREIGN KEY (id_wunsch) REFERENCES wunsch(id)
      );
      
      /* INSERT INTO geschenk (id_wunsch,name_schenker)
           VALUES
           ()
           */
         
       commit;