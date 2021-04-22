CREATE OR REPLACE FUNCTION populate_classes
(name character varying, init_time timestamp without time zone, end_time timestamp without time zone, init_hour integer, vacancies integer, dow_end_hour integer, weekend_end_hour integer)
RETURNS timestamp without time zone
LANGUAGE plpgsql
AS $function$
DECLARE
    end_hour INTEGER;
begin
	WHILE init_time < end_time
    LOOP
    
    IF date_part('dow', init_time) BETWEEN 1 AND 5
    THEN
    	end_hour = dow_end_hour;
    ELSE
    	end_hour = weekend_end_hour;
    END IF;
    
    IF NOT date_part('hour', init_time) < init_hour AND NOT date_part('hour', init_time) > end_hour AND date_part('hour', init_time) <> end_hour
    THEN    		
    	INSERT INTO classes(class_name, start_date, end_date, filled_vacancies, total_vacancies)
        VALUES (name, init_time, init_time + interval '55 minutes', 0, vacancies);
    END IF;
    
    init_time = init_time + interval '1 hour';
    IF date_part('hour', init_time) > end_hour
    THEN
    	WHILE date_part('hour', init_time) <> init_hour
      LOOP
      	init_time = init_time + interval '1 hour';
      END LOOP;       
    END IF;
    
    END LOOP;
RETURN init_time;
end;
$function$;

SELECT populate_classes('FitDance', '2021-04-21 00:00:00', '2022-04-21 00:00:00', 16, 15, 17, 16);
SELECT populate_classes('FitDance', '2021-04-21 00:00:00', '2022-04-21 00:00:00', 19, 15, 20, 19);
SELECT populate_classes('Boxe', '2021-04-21 00:00:00', '2022-04-21 00:00:00', 21, 10, 22, 21);
SELECT populate_classes('Boxe', '2021-04-21 00:00:00', '2022-04-21 00:00:00', 18, 10, 19, 18);
SELECT populate_classes('Aula de Ginástica', '2021-04-21 00:00:00', '2022-04-21 00:00:00', 6, 30, 7, 6);
SELECT populate_classes('Aula de Ginástica', '2021-04-21 00:00:00', '2022-04-21 00:00:00', 9, 30, 10, 10);
SELECT populate_classes('Treino de Musculação', '2021-04-21 00:00:00', '2022-04-21 00:00:00', 5, 30, 23, 12);