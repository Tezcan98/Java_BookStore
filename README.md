# Java_BookStory
Database lectures homework

Database Roles adjusted as 
GRANT ALL PRIVILEGES ON public.kitaplar TO personel;
GRANT select ON ALL TABLES IN SCHEMA public TO personel;
grant delete on public.kitapsiparisi to personel;
grant delete on public.siparis to personel;
GRANT update ON public.musteri TO musteri;
grant update,insert on public.kitapsiparisi to musteri 
Grant select on public.siparis,public.kitaplar to musteri;
/grant insert  on kitapsiparisi to musteri;/
/*ALTER ROLE personel WITH LOGIN;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO admin;*/
GRANT SELECT ON public.musteri TO musteri;

java swing is used.

There is no database file in reposity, you can rewrite with looking at code
