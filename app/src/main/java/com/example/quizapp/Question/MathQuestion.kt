package com.example.quizapp.Question

class MathQuestion {

    public  fun getAllQuestions():ArrayList<Question>{
        var list = ArrayList<Question>()

        //1
        list.add(
            Question(
            "ចូរគណនាកន្សោមខាងក្រោម\n" +
                    "2-4(2x6) = ?"
                 ,"-46"
                 ,"46"
                ,"-24"
                ,"-46"
        ))

        //2
        list.add(Question(
            "ចូរគណនាកន្សោមខាងក្រោម\n" +
                    "2+4(2x6) = ?"
            ,"72"
            ,"60"
            ,"50"
            ,"50"
        ))

        //3
        list.add(Question(
            "ចូរគណនាកន្សោមខាងក្រោម\n" +
                    "2x4 + 5(2x6) = ?"
            ,"156"
            ,"68"
            ,"176"
            ,"68"
        ))
        //4
        list.add(Question(
            "25% នៃ 150 ស្មើនិង? "
            ,"37.5"
            ,"50"
            ,"35"
            ,"37.5"
        ))

        //5
        list.add(Question(
            "20% នៃ 108 ស្មើនិង? "
            ,"21.6"
            ,"22.6"
            ,"24.6"
            ,"21.6"
        ))

        //6
        list.add(Question(
            "ចូរគណនាតម្លៃ x \nបើ 3x + 6 =0 "
            ,"2"
            ,"-2"
            ,"1"
            ,"-2"
        ))

        //6
        list.add(Question(
            "ចូរគណនាតម្លៃ x \nបើ 8x + 4 =4x "
            ,"2"
            ,"1"
            ,"-1"
            ,"-1"
        ))

        //7
        list.add(Question(
            "ចូរគណនាតម្លៃ x \nបើ 9x + 6 =3 "
            ,"-1/3"
            ,"1/3"
            ,"-3"
            ,"-1/3"
        ))

        //8
        list.add(Question(
            "ចូរគណនាតម្លៃ x \nបើ 5x - 5 =3 "
            ,"-8/5"
            ,"8/3"
            ,"8/5"
            ,"8/5"
        ))

        //9
        list.add(Question(
            "ចូរគណនាបរិមាណចតុកោណកែង\n" +
                    "បើ x=10 , y=12 ?"
            ,"44"
            ,"22"
            ,"36"
            ,"44"
        ))

        //10
        list.add(Question(
            "ចូរគណនាបរិមាណចតុកោណកែង\n" +
                    "បើ x=10.5 , y=14 ?"
            ,"24.5"
            ,"49"
            ,"37.5"
            ,"49"
        ))


        return list
    }
}