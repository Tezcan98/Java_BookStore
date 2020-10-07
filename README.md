# Java_BookStory
Database lectures homework 
  
 Database Roles adjusted as  <br>
GRANT ALL PRIVILEGES ON public.kitaplar TO personel; <br>
GRANT select ON ALL TABLES IN SCHEMA public TO personel;  <br>
grant delete on public.kitapsiparisi to personel;  <br>
grant delete on public.siparis to personel;  <br>
GRANT update ON public.musteri TO musteri;  <br>
grant update,insert on public.kitapsiparisi to musteri  <br>
Grant select on public.siparis,public.kitaplar to musteri; <br>
/grant insert  on kitapsiparisi to musteri;/ <br>
/*ALTER ROLE personel WITH LOGIN; <br>
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO admin;*/ <br>
GRANT SELECT ON public.musteri TO musteri; <br>

java swing is used. <br>

There is no database file in reposity, you can rewrite with looking at code <br>
