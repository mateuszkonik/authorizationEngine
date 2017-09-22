Struktura projektu wygląda następująco:

	- w folderze api znajdują się wszystkie zapytania pogrupowane w oddzielnych folderach. 
	W każdym folderze znajduje się klasa o tej samej nazwie - jest to klasa główna dla danej kategorii.
	Są w niej zawarte wszystkie zapytania API, razem z opisami dla swaggera i potrzebnymi adnotacjami dla MVC.
	Cała logika znajduje się w osobnych klasach których nazwa jest taka jest nazwa metody która ją wywołuje.
	Czyli np. metoda API login(...) wywołuje metodę respond klasy Login, która wykonuje wszystko co trzeba.

	- w folderze models znajdują się głownie schematy requestów i responsów na potrzebny konkretnych metod.
	W podfolderze data znajdują się klasy z adnotacjami cassandry(?).

	- w folderze response znajdują się klasy powstałe poprzez rozbicie StatusResponse na mniejsze części,
	ale działanie jest identyczne

	- w folderze util znajdują się wszystkie klasy *tools, wyjątki itd.

	- folder mock jest stworzony jedynie na potrzeby testów i tak naprawdę jest do usunięcia

problemy, których nie dałem rady naprawić:
	- brak biblioteki auth_engine_java_client - do tego celu stworzyłem w/w mockowe klasy
 
całkowicie zrezygnowałem z użycia app_id w zapytaniach, ale jako że jest on potrzebny dla Configu w cassandrze to ustaliłem
go na sztywno jako stałą w klasie Main, pod nazwą APP_ID. W tej klasie jest też kilka zmiennych konfiguracyjnych.
