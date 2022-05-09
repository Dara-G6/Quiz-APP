package com.example.quizapp.Question

import android.util.Log
import kotlin.random.Random

class ScienceQuestion {
    public fun getAllQuestions():ArrayList<Question>{
        val list = ArrayList<Question>()

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “លីចូម” នៅក្នុងតារាងខួបគីមី?",
                "Li",
                "Be",
                "Mg",
                "Li"
            )
        )


        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “ទីតាន” នៅក្នុងតារាងខួបគីមី?",
                "Zr",
                "Ti",
                "Nb",
                "Ti"
            )
        )


        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “អុីដ្រូសែន” នៅក្នុងតារាងខួបគីមី?",
                "Ca",
                "H",
                "K",
                "H"
            )
        )


        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “ដែក” នៅក្នុងតារាងខួបគីមី?",
                "Ni",
                "Zn",
                "Fe",
                "Fe"
            )
        )


        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “ប្រាក់” នៅក្នុងតារាងខួបគីមី?",
                "Ag",
                "Au",
                "Mg",
                "Ag"
            )
        )


        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “ទង់ដែង” នៅក្នុងតារាងខួបគីមី?",
                "Pt",
                "Au",
                "Cu",
                "Cu"
            )
        )


        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “ប្លាទីន” នៅក្នុងតារាងខួបគីមី?",
                "Ir",
                "Co",
                "Pt",
                "Pt"
            )
        )


        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “ផូស្វ័រ” នៅក្នុងតារាងខួបគីមី?",
                "Kr",
                "At",
                "P",
                "P"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “ភ្លុយអរ” នៅក្នុងតារាងខួបគីមី?",
                "O",
                "F",
                "P",
                "F"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “ក្រូម” នៅក្នុងតារាងខួបគីមី?",
                "Sg",
                "Cr",
                "Mo",
                "Cr"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុ" +
                        " “អាសុីតក្លរីឌ្រិច” ?",
                "NaOH",
                "Nacl",
                "HCl",
                "HCl"
            )
        )


        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “ក្លរ” នៅក្នុងតារាងខួបគីមី?",
                "Cl",
                "I",
                "Rn",
                "Cl"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “អាសូត” នៅក្នុងតារាងខួបគីមី?",
                "Si",
                "N",
                "As",
                "N"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “ប៉ូតាស្យូម” នៅក្នុងតារាងខួបគីមី?",
                "K",
                "Na",
                "Sc",
                "K"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “អុកសុីសែន” នៅក្នុងតារាងខួបគីមី?",
                "O",
                "N",
                "H",
                "O"
            )
        )


        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “ឡង់តាន” នៅក្នុងតារាងខួបគីមី?",
                "La",
                "Na",
                "Hf",
                "La"
            )
        )


        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “អាក់ទីញ៉ូម” នៅក្នុងតារាងខួបគីមី?",
                "In",
                "Ag",
                "Ac",
                "Ac"
            )
        )


        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “កាល្យូម” នៅក្នុងតារាងខួបគីមី?",
                "Ga",
                "Ge",
                "Hg",
                "Ga"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “បរ” នៅក្នុងតារាងខួបគីមី?",
                "P",
                "B",
                "C",
                "B"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “រុយតេញ៉ូម” នៅក្នុងតារាងខួបគីមី?",
                "Re",
                "Rh",
                "Ru",
                "Ru"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “ស័ង្កសី” នៅក្នុងតារាងខួបគីមី?",
                "Ni",
                "Zn",
                "Al",
                "Zn"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “តិចនេចូម” នៅក្នុងតារាងខួបគីមី?",
                "Tc",
                "Pd",
                "Re",
                "Tc"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “អូសម្ញូ៉ម” នៅក្នុងតារាងខួបគីមី?",
                "S",
                "Mt",
                "Os",
                "Os"
            )
        )


        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “នេអុង” នៅក្នុងតារាងខួបគីមី?",
                "Ne",
                "Ar",
                "He",
                "Ne"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “អាកុង” នៅក្នុងតារាងខួបគីមី?",
                "Se",
                "Ar",
                "Br",
                "Ar"
            )
        )
        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “ព្លុយតូញ៉ូម” នៅក្នុងតារាងខួបគីមី?",
                "Pu",
                "Eu",
                "U",
                "Pu"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “សំណបា៉ហាំង” នៅក្នុងតារាងខួបគីមី?",
                "Sn",
                "In",
                "Sb",
                "Sn"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “សុីលីចូម” នៅក្នុងតារាងខួបគីមី?",
                "Cd",
                "Si",
                "Bi",
                "Si"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “សេណុង” នៅក្នុងតារាងខួបគីមី?",
                "Xe",
                "Se",
                "Bi",
                "Xe"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “សំណ” នៅក្នុងតារាងខួបគីមី?",
                "P",
                "Pb",
                "Bi",
                "Pb"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “ប្រូម” នៅក្នុងតារាងខួបគីមី?",
                "Br",
                "Ba",
                "Bi",
                "Br"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “អ៊ុយរ៉ានីញ៉ូម” នៅក្នុងតារាងខួបគីមី?",
                "Pu",
                "Nd",
                "U",
                "U"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “រ៉ាដុង” នៅក្នុងតារាងខួបគីមី?",
                "Ir",
                "Rn",
                "Rh",
                "Rn"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “គ្រីបតុង” នៅក្នុងតារាងខួបគីមី?",
                "Br",
                "Cr",
                "Kr",
                "Kr"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “កាល់ស្យូម” នៅក្នុងតារាងខួបគីមី?",
                "Na",
                "Ca",
                "K",
                "Ca"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “សេស្យូម” នៅក្នុងតារាងខួបគីមី?",
                "Zr",
                "Cs",
                "Sr",
                "Cs"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “សេរ៉្យូម” នៅក្នុងតារាងខួបគីមី?",
                "Ca",
                "Cs",
                "Ce",
                "Ce"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “ម៉ូលីបដែន” នៅក្នុងតារាងខួបគីមី?",
                "Mn",
                "Mo",
                "Nd",
                "Mo"
            )
        )


        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “តិចនេចូម” នៅក្នុងតារាងខួបគីមី?",
                "Tc",
                "Tl",
                "Ti",
                "Tc"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “កូបាល” នៅក្នុងតារាងខួបគីមី?",
                "Co",
                "Ca",
                "Cs",
                "Co"
            )
        )


        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “ម៉ង់កាណែស” នៅក្នុងតារាងខួបគីមី?",
                "Nb",
                "Mo",
                "Mn",
                "Mn"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “អាសេនិច” នៅក្នុងតារាងខួបគីមី?",
                "As",
                "Au",
                "Ag",
                "As"
            )
        )

        //
        list.add(
            Question(
                "តើនិមិត្តសញ្ញាមួយណាខាងក្រោមដែលតំណាងឲធាតុគីមី" +
                        " “អង់ទីម៉ាន់” នៅក្នុងតារាងខួបគីមី?",
                "Se",
                "Sb",
                "Sn",
                "Sb"
            )
        )






        val random = Random.nextInt(2)
        if (random ==0){
            list.reverse()
        }


        return list
    }
}