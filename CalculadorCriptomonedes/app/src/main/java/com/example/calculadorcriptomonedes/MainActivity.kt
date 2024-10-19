package com.example.calculadorcriptomonedes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception
import java.util.Arrays
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity(), View.OnClickListener{
    private var posicioActual=0;
    private lateinit var btn1: Button;
    private lateinit var btn2: Button;
    private lateinit var btn3: Button;
    private lateinit var btn4: Button;
    private lateinit var btn5: Button;
    private lateinit var btn6: Button;
    private lateinit var btn7: Button;
    private lateinit var btn8: Button;
    private lateinit var btn9: Button;
    private lateinit var btn0: Button;
    private lateinit var btnCE: Button;
    private lateinit var btnDel: Button;
    private lateinit var btnComa: Button;
    private lateinit var btnCV: Button;
    private lateinit var txtvIntroduit: TextView
    private lateinit var txtvResultat: TextView
    private lateinit var btnDreta: Button;
    private lateinit var btnEsquerra: Button;
    private lateinit var btnSelect: Button;

    private var arrayNomCriptos= arrayOf<String>("Bitcoin","Etherum","Tether","XRP")
    private var valorsCriptos=arrayOf<Double>(0.0,0.0,0.0,0.0)
    private var criptoInicialitzada=booleanArrayOf(true,true,true,true)
    private var bitCoinActual = -1

    private var textIntroduit: String="";
    private var comaIntroduida: Boolean = false
    private var numDecimals: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        declararButons()
        listeners()
    }

    fun declararButons(){
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)
        btn6 = findViewById(R.id.btn6)
        btn7 = findViewById(R.id.btn7)
        btn8 = findViewById(R.id.btn8)
        btn9 = findViewById(R.id.btn9)
        btn0 = findViewById(R.id.btn0)
        btnDel = findViewById(R.id.btnDel)
        btnComa = findViewById(R.id.btnComa)
        btnCE = findViewById(R.id.btnCE)
        btnCV = findViewById(R.id.btnCV)
        txtvIntroduit = findViewById(R.id.txtInsertat)
        txtvResultat = findViewById(R.id.txtResultat)
        btnDreta = findViewById(R.id.btnDre)
        btnEsquerra = findViewById(R.id.btnEsq)
        btnSelect = findViewById(R.id.selector)

    }

    fun listeners(){
        btn1.setOnClickListener(){
            afegirValor("1")
        }
        btn2.setOnClickListener(){
            afegirValor("2")
        }
        btn3.setOnClickListener(){
            afegirValor("3")
        }
        btn4.setOnClickListener(){
            afegirValor("4")
        }
        btn5.setOnClickListener(){
            afegirValor("5")
        }
        btn6.setOnClickListener(){
            afegirValor("6")
        }
        btn7.setOnClickListener(){
            afegirValor("7")
        }
        btn8.setOnClickListener() {
            afegirValor("8")
        }
        btn9.setOnClickListener(){
            afegirValor("9")
        }
        btn0.setOnClickListener(){
            afegirValor("0")
        }
        btnComa.setOnClickListener(){
            afegirValor(",")
        }
        btnCE.setOnClickListener(){
            clearText()
        }
        btnDel.setOnClickListener(){
            eliminarValor()
        }
        btnCV.setOnClickListener(){
            afegirValor(",")
        }
        btnDreta.setOnClickListener(){
            moureCursor(true)
        }
        btnEsquerra.setOnClickListener(){
            moureCursor(false)
        }
        btnCV.setOnClickListener(){
            canviarValor()
        }
        btnSelect.setOnClickListener(){
            seleccionarCripto()
        }
    }



    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }



    fun afegirValor(valor: String){
        var textActual: String = textIntroduit
        val llarg: Int = textIntroduit.length

        if(valor != ","){
            if(!comaIntroduida) {
                textActual=calcularPosicioActual(llarg,valor)

            }else{
                if(numDecimals<2){
                    if(numDecimals>=posicioActual) {
                        numDecimals++
                        textActual=calcularPosicioActual(llarg,valor)
                    }
                }else{
                    if(posicioActual>numDecimals)  {
                        textActual = calcularPosicioActual(llarg, valor)
                    }
                }
            }
        }else{
            if(!comaIntroduida && posicioActual<=2){
                comaIntroduida=true
                textActual=calcularPosicioActual(llarg,valor)
                numDecimals=posicioActual
            }
        }

        txtvIntroduit.text=textActual
        textIntroduit=textActual
        modificarResultat()

    }

    fun calcularPosicioActual(llarg: Int, valor: String): String {
        var nouNum: String=""
        for(i in 0 until llarg){
            if(i!=llarg-1-posicioActual){
                nouNum+= textIntroduit[i]
            }else{
                nouNum+= textIntroduit[i]
                nouNum+=valor
            }
        }
        if(llarg==0){
            if(valor.equals(",")){
                nouNum="0,"
            }else{
                nouNum=valor
            }
        }
        return nouNum
    }

    fun clearText(){
        textIntroduit=""
        txtvIntroduit.text="0"
        numDecimals=0
        comaIntroduida=false
        posicioActual=0
    }

    fun eliminarValor(){
        var llarg = textIntroduit.length
        var nouNum="";
        for(i in 0 until llarg){
            if(i!=llarg-1-posicioActual){
                nouNum+=textIntroduit.get(i)
            }else{
                if(comaIntroduida){
                    if(numDecimals>=posicioActual) {
                        numDecimals--
                    }
                }
                if(textIntroduit[i]==','){
                    comaIntroduida=false
                }
            }
        }
        if(llarg-1==0){
            txtvIntroduit.text="0"
        }else{
            txtvIntroduit.text=nouNum
            textIntroduit=nouNum
        }
        modificarResultat()
    }

    fun moureCursor(endavant: Boolean){
        if(endavant){
            if(posicioActual>0){
                posicioActual--
            }
        }else{
            if(posicioActual<textIntroduit.length){
                posicioActual++
            }
        }
    }



    fun canviarValor(){
        if(bitCoinActual!=-1){
            var valCrip: EditText = EditText(this)
            var nouValorStr: String
            MaterialAlertDialogBuilder(this)
                .setTitle("@string/cv_title")
                .setCancelable(false)
                .setMessage(resources.getString(R.string.cv_qstn))
                .setView(valCrip)
                .setNegativeButton(resources.getString(R.string.cv_negative_btn),null) //TODO canviar text per valor de arxiu xml indicat
                .setPositiveButton(resources.getString(R.string.cv_positive_btn)) { dialog, which ->
                    try {
                        nouValorStr=valCrip.text.toString().replace(",",".")
                        valorsCriptos.set(bitCoinActual,nouValorStr.toDouble())
                        criptoInicialitzada[bitCoinActual]=false
                        //Actualtzar el valor
                        modificarResultat()
                    }catch (error: Exception){
                        (this as MainActivity).supportActionBar?.setTitle(resources.getString(R.string.cv_bad_answer))
                    }
                }
                .show()
        }else{
             (this as MainActivity).supportActionBar?.setTitle(resources.getString(R.string.cv_no_cripto_sel))
        }
    }

    fun seleccionarCripto(){

        var cont=0

        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.sel_crip_qstn))
            .setItems(arrayNomCriptos) {dialog, optionSelected ->
                bitCoinActual=optionSelected
                if(criptoInicialitzada[optionSelected]){
                    canviarValor()
                }

                //this.supportActionBar?.setTitle("Opció seleccionada $optionSelected $itemSelected")
            }
            .show()
        modificarResultat()
    }

    fun modificarResultat(){
        var strVal: String
        if(bitCoinActual!=-1){
            strVal=(valorsCriptos.get(bitCoinActual)*textIntroduit.replace(",",".").toDouble()).toString()
            txtvResultat.text=strVal.replace(".",",")
        }
    }
    fun crearCripto() {
        //TODO
    }
}