# elevator-demo
Concurrency elevator

 Программа запускается из командной строки, в качестве параметров задается:
  - кол-во этажей в подъезде — N (от 5 до 20);
  - высота одного этажа;
  - скорость лифта при движении в метрах в секунду (ускорением пренебрегаем, считаем, что когда лифт едет — он сразу едет с определенной скоростью);
  - время между открытием и закрытием дверей.
 
  После запуска программа должна постоянно ожидать ввода от пользователя и выводить действия лифта в реальном времени. События, которые нужно выводить:
  - лифт проезжает некоторый этаж;
  - лифт открыл двери;
  - лифт закрыл двери.
 
  Возможный ввод пользователя:
  - вызов лифта на этаж из подъезда;
  - нажать на кнопку этажа внутри лифта.
 
  Считаем, что пользователь не может помешать лифту закрыть двери.
  Все данные, которых не хватает в задаче, можно выбрать на свое усмотрение.
 
