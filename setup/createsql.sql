CREATE DATABASE jobtime
  WITH ENCODING='UTF8'
       CONNECTION LIMIT=-1;

       
CREATE TABLE public.job
(
   id uuid, 
   name text, 
   isdefault boolean, 
   CONSTRAINT pkey PRIMARY KEY (id)
) 
WITH (
  OIDS = FALSE
)
;

CREATE TABLE public.mission
(
   id uuid, 
   name text, 
   isdefault boolean, 
   job_id uuid, 
   FOREIGN KEY (job_id) REFERENCES job (id) ON UPDATE NO ACTION ON DELETE NO ACTION, 
   CONSTRAINT pmissionkey PRIMARY KEY (id)
) 
WITH (
  OIDS = FALSE
)
;



CREATE TABLE project
(
  id uuid NOT NULL,
  isdefault boolean,
  name text,
  mission_id uuid,
  CONSTRAINT pprojectkey PRIMARY KEY (id),
  CONSTRAINT project_mission_id_fkey FOREIGN KEY (mission_id)
      REFERENCES mission (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE project
  OWNER TO postgres;

  
  
CREATE TABLE public.jobdone
(
   id uuid, 
   note text, 
   date date, 
   timespent integer, 
   project_id uuid,
   tasktype_id uuid,
   CONSTRAINT pjobspentkey PRIMARY KEY (id), 
   FOREIGN KEY (project_id) REFERENCES project (id) ON UPDATE NO ACTION ON DELETE NO ACTION
) 
WITH (
  OIDS = FALSE
)
;


CREATE TABLE public.tasktype
(
   id uuid, 
   type text, 
   CONSTRAINT ptasktypekey PRIMARY KEY (id)
) 
WITH (
  OIDS = FALSE
)
;


ALTER TABLE jobdone
   ALTER COLUMN timespent TYPE real;

