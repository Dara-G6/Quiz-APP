package com.example.quizapp.Question

import android.util.Log
import kotlin.random.Random

class GeneralQuestion {
    public fun getAllQuestions():ArrayList<Question>{
        val list = ArrayList<Question>()

        //
        list.add(Question(
            "តើក្នុងចំណោម មហាសមុទ្រខាងក្រោមមួយណាដែលមានទំហំធំជាងគេ?"
            ,"មហាសមុទ្រ ឥណ្ឌា"
            ,"មហាសមុទ្រ អាត្លង់តិច"
            ,"មហាសមុទ្រ ប៉ាសុីភិច"
            ,"មហាសមុទ្រ ប៉ាសុីភិច"
        ))

        //
        list.add(Question(
            "តើក្នុងចំណោម ប្រទេសខាងក្រោមមួយណាដែលមិនមែនស្ថិតនៅក្នងទ្វីបអាមេរិកខាងត្បូង?"
            ,"ប្រទេសប៉ារ៉ាហ្គាយ"
            ,"ប្រទេសឈីលី"
            ,"ប្រទេសកូស្តារីកា"
            ,"ប្រទេសកូស្តារីកា"
        ))


        //
        list.add(Question(
            "តើប្រទេសព័រទុយហ្គាល់ ស្ថិតនៅក្នុងទ្វីបអ្វី?"
            ,"ទ្វីបអឺរ៉ុប"
            ,"ទ្វីបអាស៊ី"
            ,"ទ្វីបអាហ្វ្រិក"
            ,"ទ្វីបអឺរ៉ុប"
        ))


        //
        list.add(Question(
            "តើប្រទេសមួយណា ដែលមានទំហំផ្ទៃដីធំជាងគេ លំដាប់ទី២ក្នុងពិភពលោក?"
            ,"ប្រទេសអូស្ត្រាលី"
            ,"ប្រទេសកាណាដា"
            ,"ប្រទេសចិន"
            ,"ប្រទេសកាណាដា"
        ))


        //
        list.add(Question(
            "តើក្នុងចំណោម ប្រទេសខាងក្រោមមួយណាដែលមិនមែនស្ថិតនៅក្នងទ្វីបអាសុី?"
            ,"ប្រទេសថៃ"
            ,"ប្រទេសរុស្សី"
            ,"ប្រទេសឥណ្ឌូនេស៊ី"
            ,"ប្រទេសរុស្សី"
        ))



        //
        list.add(Question(
            "តើប្រទេសប្រេសុីល ស្ថិតនៅក្នុងទ្វីបអ្វី?"
            ,"ទ្វីបអឺរ៉ុប"
            ,"ទ្វីបអាមេរិកខាងជើង"
            ,"ទ្វីបអាមេរិកខាងត្បូង"
            ,"ទ្វីបអាមេរិកខាងត្បូង"
        ))



        //
        list.add(Question(
            "តើក្នុងចំណោម ប្រទេសខាងក្រោមមួយណាដែលមិនមែនស្ថិតនៅក្នងទ្វីបអាមេរិកខាងជើង?"
            ,"ប្រទេសដូមីនីកា"
            ,"ប្រទេសអែលសាល់វ៉ាឌ័រ"
            ,"ប្រទេសអេត្យូពី"
            ,"ប្រទេសអេត្យូពី"
        ))


        //
        list.add(Question(
            "តើប្រទេសម៉ុលដាវី ស្ថិតនៅក្នុងទ្វីបអ្វី?"
            ,"ទ្វីបអឺរ៉ុប"
            ,"ទ្វីបអាស៊ី"
            ,"ទ្វីបអាហ្វ្រិក"
            ,"ទ្វីបអឺរ៉ុប"
        ))


        //
        list.add(Question(
            "តើប្រទេសកាណាដា ស្ថិតនៅក្នុងទ្វីបអ្វី?"
            ,"ទ្វីបអឺរ៉ុប"
            ,"ទ្វីបអាមេរិកខាងជើង"
            ,"ទ្វីបអាមេរិកខាងត្បូង"
            ,"ទ្វីបអាមេរិកខាងជើង"
        ))


        //
        list.add(Question(
            "តើប្រទេសអាល់ហ្សេរី ស្ថិតនៅក្នុងទ្វីបអ្វី?"
            ,"ទ្វីបអាមេរិកខាងជើង"
            ,"ទ្វីបអាស៊ី"
            ,"ទ្វីបអាហ្វ្រិក"
            ,"ទ្វីបអាហ្វ្រិក"
        ))


        //
        list.add(Question(
            "តើក្នុងចំណោម ប្រទេសខាងក្រោមមួយណាដែលមិនមែនស្ថិតនៅក្នងទ្វីបអឺរ៉ុប?"
            ,"ប្រទេសអេស្សុីប"
            ,"ប្រទេសប៉ូឡូញ"
            ,"ប្រទេសអេស្ប៉ាញ"
            ,"ប្រទេសអេស្សុីប"
        ))


        //
        list.add(Question(
            "តើរដ្ឋធានី របស់ប្រទេសអាល្លឺម៉ង់មានឈ្មោះអ្វី?"
            ,"អេសសិន"
            ,"ខឹឡូន"
            ,"ប៊ែកឡាំង"
            ,"ប៊ែកឡាំង"
        ))


        //
        list.add(Question(
            "តើក្នុងចំណោម ប្រទេសខាងក្រោមមួយណាដែលមិនមែនស្ថិតនៅក្នងទ្វីបអាសុី?"
            ,"ប្រទេសចិន"
            ,"ប្រទេសប៉ាគីស្ថាន"
            ,"ប្រទេសបារាំង"
            ,"ប្រទេសបារាំង"
        ))


        //
        list.add(Question(
            "តើប្រទេសប៉ូឡូញ  ស្ថិតនៅក្នុងទ្វីបអ្វី?"
            ,"ទ្វីបអឺរ៉ុប"
            ,"ទ្វីបអាស៊ី"
            ,"ទ្វីបអាហ្វ្រិក"
            ,"ទ្វីបអឺរ៉ុប"
        ))


        //
        list.add(Question(
            "តើរដ្ឋធានី របស់ប្រទេសអាមេរិកមានឈ្មោះអ្វី?"
            ,"ញ៉ូយ៉ក"
            ,"វា៉ស៊ីនតោនឌីស៊ី"
            ,"សាន់ហ្រ្វាន់ស៊ីស្កូ"
            ,"វា៉ស៊ីនតោនឌីស៊ី"
        ))


        //
        list.add(Question(
            "តើក្នុងចំណោម ប្រទេសខាងក្រោមមួយណាដែលមិនមែនស្ថិតនៅក្នងទ្វីបអឺរ៉ុប?"
            ,"ប្រទេសអេស្តូនី"
            ,"ប្រទេសប៉ូឡូញ"
            ,"ប្រទេសកាហ្សាក់ស្ថាន"
            ,"ប្រទេសកាហ្សាក់ស្ថាន"
        ))

        //
        list.add(Question(
            "តើប្រទេសគុយបា ស្ថិតនៅក្នុងទ្វីបអ្វី?"
            ,"ទ្វីបអឺរ៉ុប"
            ,"ទ្វីបអាមេរិកខាងជើង"
            ,"ទ្វីបអាមេរិកខាងត្បូង"
            ,"ទ្វីបអាមេរិកខាងជើង"
        ))

        //
        list.add(Question(
            "តើក្នុងចំណោម ប្រទេសខាងក្រោមមួយណាដែលមិនមែនជាសមាជិក អាស៊ាន?"
            ,"ប្រទេសថៃ"
            ,"ប្រទេសវៀតណាម"
            ,"ប្រទេសជប៉ុន"
            ,"ប្រទេសជប៉ុន"
        ))

        //
        list.add(Question(
            "តើប្រទេសព័រទុយហ្គាល់ ស្ថិតនៅក្នុងទ្វីបអ្វី?"
            ,"ទ្វីបអឺរ៉ុប"
            ,"ទ្វីបអាស៊ី"
            ,"ទ្វីបអាហ្វ្រិក"
            ,"ទ្វីបអឺរ៉ុប"
        ))

        val random = Random.nextInt(2)
        if (random ==0){
            list.reverse()
        }
        Log.d("Size ",list.size.toString())
        return list
    }
}