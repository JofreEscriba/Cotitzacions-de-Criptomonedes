package com.example.calculadorcriptomonedes

import android.graphics.ColorSpace.Rgb
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.util.Arrays
import kotlin.Exception
import kotlin.reflect.typeOf
import java.math.BigDecimal
import java.math.RoundingMode
import android.widget.LinearLayout
import android.text.InputType

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
    private var arrayNomCriptos = arrayListOf<String>()
    private var valorsCriptos=arrayListOf<String>("0.0","0.0","0.0","0.0")
    private var criptoInicialitzada= arrayListOf<String>("true","true","true","true")
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
        arrayNomCriptos.add("Bitcoin")
        arrayNomCriptos.add("Etherum")
        arrayNomCriptos.add("Tether")
        arrayNomCriptos.add("XRP")
        arrayNomCriptos.add(resources.getString(R.string.new_btcn))
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
        if(textActual=="0"){
            textActual=""
        }
        if(valor != ","){
            if(!comaIntroduida) {

                textActual=calcularPosicioActual(valor)

            }else{
                if(numDecimals<2){
                    if(numDecimals>=posicioActual) {
                        numDecimals++


                    }
                    textActual=calcularPosicioActual(valor)
                }else{
                    if(posicioActual>numDecimals)  {
                        textActual = calcularPosicioActual( valor)
                    }else{
                        textActual=calcularPosicioActual("")
                    }
                }
            }
        }else{
            if(!comaIntroduida && posicioActual<=2){
                comaIntroduida=true
                textActual=calcularPosicioActual(valor)
                numDecimals=posicioActual
            }
        }

        txtvIntroduit.text=textActual
        textIntroduit=textActual

        modificarResultat()
    }

    fun calcularPosicioActual( valor: String): String {
        var nouNum: String=""

        if(textIntroduit.equals("")){
            if(valor.equals(",")){
                nouNum="0,"
            }else{
                nouNum=valor
            }
        }else{
            textIntroduit=textIntroduit.replace("|","")
            for(i in 0 until textIntroduit.length){
                if(i!=textIntroduit.length-1-posicioActual && posicioActual-textIntroduit.length!=i ){
                    nouNum+= textIntroduit[i]
                }else{
                    if(posicioActual==textIntroduit.length){
                        nouNum += valor + "|"
                        nouNum += textIntroduit[i]
                    }else {
                        nouNum += textIntroduit[i]
                        nouNum += valor + "|"
                    }
                }
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
        if(bitCoinActual!=-1) {
            txtvResultat.text = "0"
        }
    }

    fun eliminarValor(){
        var llarg = textIntroduit.length
        var nouNum="";
        for(i in 0 until llarg){
            if(i!=llarg-2-posicioActual){
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
            if(posicioActual<=textIntroduit.length){
                posicioActual++

            }
        }
        afegirValor("")

    }

    fun canviarValor(): Boolean{
        var valorCanviat=false
        if(bitCoinActual!=-1){
            var valCrip: EditText = EditText(this).apply{
                inputType= InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

            }
            var nouValorStr: String

            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.cv_title))
                .setCancelable(false)
                .setMessage(resources.getString(R.string.cv_qstn))
                .setView(valCrip)
                .setNegativeButton(resources.getString(R.string.cv_negative_btn),null)
                .setPositiveButton(resources.getString(R.string.cv_positive_btn)) { dialog, which ->
                    try {
                        nouValorStr=valCrip.text.toString().replace(",",".")
                        nouValorStr=nouValorStr.replace("|","")
                        valorsCriptos.set(bitCoinActual,nouValorStr)
                        criptoInicialitzada[bitCoinActual]=false.toString()
                        //Actualtzar el valor
                        modificarResultat()
                        valorCanviat=true
                    }catch (error: Exception){
                        alertaErr(resources.getString(R.string.cv_bad_answer))
                    }
                }
                .show()
        }else{
            alertaErr(resources.getString(R.string.cv_no_cripto_sel))

        }

        return valorCanviat
    }

    fun seleccionarCripto(){
        var nouArray = arrayOfNulls<String>(arrayNomCriptos.size)
        var index=0
        for (nomCripto in arrayNomCriptos){
            nouArray[index]=nomCripto
            index++
        }
        var cont=0
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.sel_crip_qstn))
            .setItems(nouArray) {dialog, optionSelected ->

                if(optionSelected==arrayNomCriptos.size-1){
                    crearCripto(optionSelected)
                }else if(criptoInicialitzada[optionSelected].toBoolean()){
                    bitCoinActual=optionSelected
                    canviarValor()
                }else{
                    bitCoinActual=optionSelected
                }
                //this.supportActionBar?.setTitle("Opció seleccionada $optionSelected $itemSelected")
            }
            .show()
        modificarResultat()
    }

    fun modificarResultat(){
        var strVal: String
        if(bitCoinActual!=-1){
            if(textIntroduit==""){
                strVal=textIntroduit.replace("|","")
                strVal=BigDecimal(valorsCriptos.get(bitCoinActual).toDouble()*strVal.replace(",",".").toDouble()).setScale(5, RoundingMode.HALF_EVEN).toString()
            }else{
                strVal="0"
            }

            if(strVal=="0"){
                txtvResultat.text = "0"
            }else {
                txtvResultat.text = strVal.replace(".", ",")
            }
        }
    }
    fun crearCripto(currPosition:Int) {
        var nomCrip: EditText = EditText(this)
        var nom: String
        var nomUtilitzat=false

        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.new_btcn))
            .setCancelable(false)
            .setMessage(resources.getString(R.string.new_btcn_name))
            .setView(nomCrip)
            .setNegativeButton(resources.getString(R.string.cv_negative_btn),null)
            .setPositiveButton(resources.getString(R.string.cv_positive_btn)) { dialog, which ->
                nom =nomCrip.text.toString()
                if(nom.length!=0) {
                    for (nomBit in arrayNomCriptos){
                        if (nom.equals(nomBit)){
                            nomUtilitzat=true
                        }
                    }
                    if(nomUtilitzat){
                        alertaErr(resources.getString(R.string.err_name_used))
                    }else{
                        setValue(nom)
                    }
                }else{
                    alertaErr(resources.getString(R.string.err_name_len))
                }
            }
            .show()
    }

    fun setValue(nom: String): Boolean{
        var valCrip: EditText = EditText(this).apply {
            inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
        }
        var nouValorStr: String
        var bool: Boolean = false
        var valor: Double
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.new_btcn))
            .setCancelable(false)
            .setMessage(resources.getString(R.string.new_btcn_value))
            .setView(valCrip)
            .setNegativeButton(resources.getString(R.string.cv_negative_btn),null)
            .setPositiveButton(resources.getString(R.string.cv_positive_btn)) { dialog, which ->
                try {
                    valor = valCrip.text.toString().replace(",",".").toDouble()
                    bool=true
                    valorsCriptos.add(valor.toString())
                    criptoInicialitzada.add("false") //el valor és false en cas d'estar inicialitzada
                    arrayNomCriptos.set(arrayNomCriptos.size-1,nom)
                    arrayNomCriptos.add(resources.getString(R.string.new_btcn))
                }catch (err: Exception){
                    alertaErr(resources.getString(R.string.err_inv_num))
                    bool=false
                }
            }
            .show()
        return bool
    }

    fun alertaErr(titol: String){

        val parentLayout = findViewById<View>(android.R.id.content)

        val snack = Snackbar.make(parentLayout, titol, Snackbar.LENGTH_LONG)
        snack.setBackgroundTint(getResources().getColor(R.color.red))
        snack.setTextColor(getResources().getColor(R.color.white))

        snack.show();
    }

    fun carregarText(text1: String){
        txtvIntroduit.text=text1
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("textIntroduit",textIntroduit)
        outState.putBoolean("comaIntroduida",comaIntroduida)
        outState.putInt("numDecimals",numDecimals)
        outState.putInt("bitCoinActual",bitCoinActual)
        outState.putStringArrayList("criptoInicialitzada",criptoInicialitzada)
        outState.putStringArrayList("valorsCriptos",valorsCriptos)
        outState.putStringArrayList("arrayNomCriptos",arrayNomCriptos)
        outState.putInt("posicioActual",posicioActual)

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        textIntroduit=savedInstanceState.getString("textIntroduit","")
        comaIntroduida=savedInstanceState.getBoolean("comaIntroduida")
        numDecimals=savedInstanceState.getInt("numDecimals")
        bitCoinActual=savedInstanceState.getInt("bitCoinActual")
        criptoInicialitzada=savedInstanceState.getStringArrayList("criptoInicialitzada")!!
        valorsCriptos=savedInstanceState.getStringArrayList("valorsCriptos")!!
        arrayNomCriptos=savedInstanceState.getStringArrayList("arrayNomCriptos")!!
        posicioActual=savedInstanceState.getInt("posicioActual")

        carregarText(textIntroduit)
        modificarResultat()

        super.onRestoreInstanceState(savedInstanceState)
    }
}