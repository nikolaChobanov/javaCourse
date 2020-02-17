Sentiment Analysis е процесът по алгоритмично идентифициране и категоризиране на мнения, изразени в свободен текст, особено за да се определи дали отношението на автора към конкретна тема, продукт и т.н. е позитивно, негативно или неутрално.

Данните, от които се учи алгоритъмът, са множество от 8,529 филмови отзива (ревюта), за които отношението на автора е било оценено от човек по скала от 0 до 4.

Използвания data set от сайта Rotten Tomatoes, използван в Кaggle machine learning competition.

Данните са налични в текстовия файл reviews.txt, като всеки ред от файла започва с рейтинг, следван от интервал и текста на отзива.

Изчислява се sentiment score на всяка дума като средно аритметично (без закръгляване) на всички рейтинги, в които участва дадената дума. Дума е последователност от малки и главни латински букви и цифри с дължина поне един символ. Stopwords се игнорират.

По даден текст на отзив се изчислява неговият sentiment score като средно аритметично на sentiment scores на всяка дума в отзива. . Игнорират се също всички непознати думи, за които алгоритъмът не е обучен, т.е. не се срещат нито веднъж в reviews.txt. Sentiment score на отзив, в който не се съдържа нито една дума с известен sentiment score (състои се само от непознати думи и/или stopwords), се приема за -1.0 (unknown).

Методът append дава възможност да се усъвършенства sentiment analyzer. При добавяне на нови ревюта и оценки, преизчисляваме sentiment-a на думите от ревюто.