package br.grupointegrado.appmetaforadevenda.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import br.grupointegrado.appmetaforadevenda.Dao.CidadeDao;
import br.grupointegrado.appmetaforadevenda.Dao.PessoaDao;
import br.grupointegrado.appmetaforadevenda.Pessoa.Pessoa;
import br.grupointegrado.appmetaforadevenda.Pessoa.Telefone;
import br.grupointegrado.appmetaforadevenda.R;
import br.grupointegrado.appmetaforadevenda.TelaConsulta.ConsultaCidadeActivity;
import br.grupointegrado.appmetaforadevenda.Util.ConvesorUtil;
import br.grupointegrado.appmetaforadevenda.Util.FragmentTab;
import br.grupointegrado.appmetaforadevenda.Util.Mask;
import eu.inmite.android.lib.validations.form.FormValidator;
import eu.inmite.android.lib.validations.form.annotations.MaxLength;
import eu.inmite.android.lib.validations.form.annotations.MinLength;
import eu.inmite.android.lib.validations.form.annotations.NotEmpty;
import eu.inmite.android.lib.validations.form.annotations.RegExp;

import static eu.inmite.android.lib.validations.form.annotations.RegExp.EMAIL;

import eu.inmite.android.lib.validations.form.callback.SimpleErrorPopupCallback;

import static br.grupointegrado.appmetaforadevenda.Util.ConvesorUtil.stringParaDate;


public class PessoaFragment extends Fragment implements DatePickerDialog.OnDateSetListener, DialogInterface.OnCancelListener, FragmentTab {


    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};


  /*  @NotEmpty(messageId = R.string.Campo_vazio, order = 1)
    @MinLength(value = 14, messageId = R.string.Min_Value_cpf, order = 1)
    @MaxLength(value = 14, messageId = R.string.Max_Value_cpf, order = 1)
*/
    private MaterialEditText editCpf;

   /* @NotEmpty(messageId = R.string.Campo_vazio, order = 1)
    @MinLength(value = 18, messageId = R.string.Min_Value_cnpj, order = 1)
    @MaxLength(value = 18, messageId = R.string.Max_Value_cnpj, order = 1)*/

    private MaterialEditText editCnpj;

    private RadioButton radioFisica;
    private RadioButton radioJuridica;

    @MaxLength(value = 60, messageId = R.string.max_cliente, order = 2)
    @NotEmpty(messageId = R.string.Campo_vazio, order = 2)
    private MaterialEditText editNome;

    @MaxLength(value = 60, messageId = R.string.max_razaosocial, order = 2)
    @NotEmpty(messageId = R.string.Campo_vazio, order = 2)
    private MaterialEditText editRazaoSocial;

    @MaxLength(value = 60, messageId = R.string.max_Apelido, order = 3)
    @NotEmpty(messageId = R.string.Campo_vazio, order = 3)
    private MaterialEditText editApelido;

    @MaxLength(value = 60, messageId = R.string.max_fantasia, order = 3)
    @NotEmpty(messageId = R.string.Campo_vazio, order = 3)
    private MaterialEditText editFantasia;

    @NotEmpty(messageId = R.string.Campo_vazio, order = 4)
    @MinLength(value = 9, messageId = R.string.MinRG_Value, order = 4)
    @MaxLength(value = 10, messageId = R.string.MaxRG_Value, order = 4)
    private MaterialEditText editRg;

    @NotEmpty(messageId = R.string.Campo_vazio, order = 4)
    @MinLength(value = 10, messageId = R.string.MinIns_Value, order = 4)
    @MaxLength(value = 14, messageId = R.string.MaxIns_Value, order = 4)
    private MaterialEditText editInscricaEstadual;


    private MaterialEditText editDataNascimento;

    private MaterialEditText editDataAbertura;


    @NotEmpty(messageId = R.string.Campo_vazio, order = 5)
    @MaxLength(value = 50, messageId = R.string.Max_endereco, order = 5)
    private MaterialEditText editEndereco;


    @NotEmpty(messageId = R.string.Campo_vazio, order = 6)
    @MaxLength(value = 10, messageId = R.string.Max_numero, order = 6)
    private MaterialEditText editNumero;

    @MaxLength(value = 10, messageId = R.string.Max_comple, order = 7)
    private MaterialEditText editcomplemento;

    @MinLength(value =10, messageId = R.string.Min_cep, order = 8)
    @MaxLength(value =10, messageId = R.string.Max_cep, order = 8)
    @NotEmpty(messageId = R.string.Campo_vazio, order = 8)
    @RegExp(value = "[0-9]{2}.[0-9]{3}-[0-9]{3}", messageId = R.string.cep_validation, order = 8)
    private MaterialEditText editcep;


    @NotEmpty(messageId = R.string.Campo_vazio, order = 9)
    @MaxLength(value = 10, messageId = R.string.Max_bairro, order = 9)
    private MaterialEditText editBairro;

    @NotEmpty(messageId = R.string.Campo_vazio, order = 10)
    @MaxLength(value = 60, messageId = R.string.max_cidade, order = 10)
    private MaterialEditText editCidade;


    @NotEmpty(messageId = R.string.Campo_vazio, order = 11)
    private MaterialEditText editDataUltima;

    @NotEmpty(messageId = R.string.Campo_vazio, order = 12)
    private MaterialEditText editValorUltimacompra;


    @MaxLength(value = 50, messageId = R.string.Max_email, order = 13)
    @RegExp(value = EMAIL, messageId = R.string.email_validation, order = 13)
    @NotEmpty(messageId = R.string.Campo_vazio, order = 13)
    private MaterialEditText editEmail;


    @NotEmpty(messageId = R.string.Campo_vazio, order = 14)
    public MaterialEditText editDataCadastro;


    private TextWatcher cpfmask;
    private TextWatcher cnpjmask;
    private TextWatcher cepmask;


    private boolean iscpf;
    private boolean iscnpj;
    private boolean existecpf;
    private Integer cdCidade;
    private String Cidade;
    private Integer idpessoa;
    private List listatelefone;
    private Integer idCidade;
    private List<Telefone> lista_telefone;
    private boolean estadodofragment = false;


    private Pessoa pessoa;
    private Telefone telefone;
    private CidadeDao cidadedao;

    private PessoaDao clientedao;

    private Pessoa pessoaalt;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_pessoa, container, false);


        return view;
    }


    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Instancia do XML do Editext e spinner


        editCpf = (MaterialEditText) view.findViewById(R.id.edit_cpf);
        editCnpj = (MaterialEditText) view.findViewById(R.id.edit_cnpj);

        radioFisica = (RadioButton) view.findViewById(R.id.radiofisica);
        radioJuridica = (RadioButton) view.findViewById(R.id.radiojuridica);
        editRg = (MaterialEditText) view.findViewById(R.id.edit_rg);
        editRazaoSocial = (MaterialEditText) view.findViewById(R.id.edit_razaosocial);
        editInscricaEstadual = (MaterialEditText) view.findViewById(R.id.edit_inscricaoestadual);
        editFantasia = (MaterialEditText) view.findViewById(R.id.edit_nomefantasia);
        editDataAbertura = (MaterialEditText) view.findViewById(R.id.edit_data_abertura);
        editBairro = (MaterialEditText) view.findViewById(R.id.edit_bairro);

        editDataCadastro = (MaterialEditText) view.findViewById(R.id.edit_data_cadastro);
        editNumero = (MaterialEditText) view.findViewById(R.id.edit_numero);
        editCidade = (MaterialEditText) view.findViewById(R.id.edit_cidade);

        editApelido = (MaterialEditText) view.findViewById(R.id.edit_apelido);
        editEmail = (MaterialEditText) view.findViewById(R.id.edit_email);
        editNome = (MaterialEditText) view.findViewById(R.id.edit_nome);
        editDataUltima = (MaterialEditText) view.findViewById(R.id.edit_data_ultima);
        editValorUltimacompra = (MaterialEditText) view.findViewById(R.id.edit_valor_ultima_compra);
        editEndereco = (MaterialEditText) view.findViewById(R.id.edit_endereco);
        editcomplemento = (MaterialEditText) view.findViewById(R.id.edit_complemento);
        editcep = (MaterialEditText) view.findViewById(R.id.edit_cep);
        editDataNascimento = (MaterialEditText) view.findViewById(R.id.edit_data_nascimento);

        editNome.addValidator(new RegexpValidator("Nome Completo Inválido", "\\w+\\s+\\w+"));
        editNome.setValidateOnFocusLost(true);


        //colocando data de cadastro
        editDataCadastro.setText(getDateTime().toString());


        pessoa = new Pessoa();

        cidadedao = new CidadeDao(this.getActivity());

        clientedao = new PessoaDao(this.getActivity());

        cpfmask = Mask.insert("###.###.###-##", editCpf);
        editCpf.addTextChangedListener(cpfmask);
        cnpjmask = Mask.insert("##.###.###/####-##", editCnpj);
        editCnpj.addTextChangedListener(cnpjmask);
        cepmask = Mask.insert("##.###-###", editcep);
        editcep.addTextChangedListener(cepmask);

        editRg.setMaxCharacters(10);
        editInscricaEstadual.setMaxCharacters(14);



       // editCpf.addValidator(new ValidateCpf("Cpf Inválido", editCpf.getText().toString()));
       // editCpf.validate();


        //CPF|CPNJ Validacao
        editCpf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (radioFisica.isChecked()) {

                        if (editCpf.getText().toString().length() == 14){
                            iscpf = ValidaCpf(Mask.unmask(editCpf.getText().toString()));
                            if (iscpf == true) {
                                if (cpfvalidaexiste()){

                                }

                            }else {
                                editCpf.setError("CPF Inválido");
                            }
                        }else if (editCpf.getText().toString().isEmpty()){
                            editCpf.setError("CPF não pode ser vazio");
                        }else   editCpf.setError("CPF ter no minimo 11 digitos");
                    }
                }}
        });

       editCnpj.setOnFocusChangeListener(new View.OnFocusChangeListener() {
         @Override
          public void onFocusChange(View v, boolean hasFocus) {
             if (!hasFocus) {
                 if (radioJuridica.isChecked()) {
                     if (editCnpj.getText().toString().length() == 18) {
                         iscnpj = ValidaCnpj(Mask.unmask(editCnpj.getText().toString()));
                         if (iscnpj == true) {
                             if (cnpjvalidaexiste()) {

                             }
                         } else {
                             editCnpj.setError("CNPJ Inválido");
                         }
                     } else if (editCnpj.getText().toString().isEmpty()) {
                         editCnpj.setError("CNPJ não pode ser vazio");
                     } else editCnpj.setError("CNPJ ter no minimo 18 digitos");
                 }
             }
         }
      });


           editCidade = (MaterialEditText) view.findViewById(R.id.edit_cidade);
        editCidade.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    selecioarCidade();
            }
        });
        editCidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecioarCidade();
            }
        });

        radioFisica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioFisica.setChecked(true);
                radioJuridica.setChecked(false);
                RadioBoxFisica();
            }
        });
        radioJuridica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioJuridica.setChecked(true);
                radioFisica.setChecked(false);
                RadioBoxJuridica();
            }
        });

        editNome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    if (editApelido.getText().toString().isEmpty())
                        editApelido.setText(editNome.getText().toString());

            }
        });
        editRazaoSocial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    if (editFantasia.getText().toString().isEmpty())
                    editFantasia.setText(editRazaoSocial.getText().toString());
            }
        });

        editDataNascimento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    dataPicker();

            }
        });

        editDataAbertura.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    dataPicker();
            }
        });



        RadioBoxFisica();
        RadioBoxJuridica();


    }

    @Override
    public void onStart() {
        super.onStart();

        FormValidator.startLiveValidation(this, new SimpleErrorPopupCallback(this.getActivity()));
        pessoaalt = (Pessoa) getActivity().getIntent().getSerializableExtra("alterarpessoa");
        if (pessoaalt != null && estadodofragment == false) {
            setPessoalt(pessoaalt);
            estadodofragment = true;
            if (pessoaalt.getDataUltimacompra() != null) {
                editDataUltima.setVisibility(View.VISIBLE);
                editValorUltimacompra.setVisibility(View.VISIBLE);
            } else {
                editValorUltimacompra.setText("0.00");
            }
        } else {
            editValorUltimacompra.setText("0.00");
        }

    }





    private static final int REQUEST_CONSULTA_CIDADE = 1001;

    private void selecioarCidade() {
        Intent intent = new Intent(getActivity(), ConsultaCidadeActivity.class);
        intent.putExtra("selecionar_cidade", true);
        startActivityForResult(intent, REQUEST_CONSULTA_CIDADE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CONSULTA_CIDADE == requestCode && resultCode == getActivity().RESULT_OK) {
            idCidade = data.getIntExtra("cidade_id", 0);
            String nomeCidade;
            nomeCidade = cidadedao.ConsultaCidadeporid(Integer.toString(idCidade));
            editCidade.setText(nomeCidade);

            // consulta a cidade e seta na pessoa
        }
    }


    //ChecBoxFisica
    public void RadioBoxFisica() {

        if (radioFisica.isChecked()) {
            editCpf.setText("");
            editCpf.setVisibility(View.VISIBLE);
            editCnpj.setVisibility(View.GONE);

            editNome.setText("");
            editNome.setVisibility(View.VISIBLE);
            editRazaoSocial.setVisibility(View.GONE);


            editApelido.setText("");
            editApelido.setVisibility(View.VISIBLE);
            editFantasia.setVisibility(View.GONE);

            editRg.setText("");
            editRg.setVisibility(View.VISIBLE);
            editInscricaEstadual.setVisibility(View.GONE);

            editDataNascimento.setText("");
            editDataNascimento.setVisibility(View.VISIBLE);
            editDataAbertura.setVisibility(View.GONE);

            radioFisica.setChecked(true);
            radioJuridica.setChecked(false);


        }
    }

    //ChecBoxJuridica
    public void RadioBoxJuridica() {

        if (radioJuridica.isChecked()) {
            editCnpj.setText("");
            editCnpj.setVisibility(View.VISIBLE);
            editCpf.setVisibility(View.GONE);

            editRazaoSocial.setText("");
            editRazaoSocial.setVisibility(View.VISIBLE);
            editNome.setVisibility(View.GONE);


            editFantasia.setText("");
            editFantasia.setVisibility(View.VISIBLE);
            editApelido.setVisibility(View.GONE);

            editInscricaEstadual.setText("");
            editInscricaEstadual.setVisibility(View.VISIBLE);
            editRg.setVisibility(View.GONE);

            editDataAbertura.setText("");
            editDataAbertura.setVisibility(View.VISIBLE);
            editDataNascimento.setVisibility(View.GONE);

            radioFisica.setChecked(false);
            radioJuridica.setChecked(true);

        }
    }


    //Método para calcular digito verificador
    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    //Método para validar Cpf
    public static boolean ValidaCpf(String cpf) {
        if ((cpf == null) || (cpf.length() != 11) || (cpf.equals("00000000000")) ||
                (cpf.equals("11111111111")) || (cpf.equals("22222222222")) || (cpf.equals("33333333333")) ||
                (cpf.equals("44444444444")) || (cpf.equals("55555555555")) || (cpf.equals("66666666666")) ||
                (cpf.equals("77777777777")) || (cpf.equals("88888888888")) || (cpf.equals("99999999999")))
            return false;

        Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    //Método para validar Cnpj
    public static boolean ValidaCnpj(String cnpj) {
        if ((cnpj == null) || (cnpj.length() != 14) || (cnpj.equals("00000000000000")))
            return false;

        Integer digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
        Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);
        return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
    }


    public Pessoa getPessoa() {
        String cpf = null, nome = null, apelido = null,
                rg = null, datanascimento = null;

        if (radioFisica.isChecked()) {
            cpf = Mask.unmask(editCpf.getText().toString());
            nome = editNome.getText().toString();
            apelido = editApelido.getText().toString();
            rg = editRg.getText().toString();
            datanascimento = editDataNascimento.getText().toString();

        } else if (radioJuridica.isChecked()) {
            cpf = Mask.unmask(editCnpj.getText().toString());
            nome = editRazaoSocial.getText().toString();
            apelido = editFantasia.getText().toString();
            rg = editInscricaEstadual.getText().toString();
            datanascimento = editDataAbertura.getText().toString();

        }
        return new Pessoa(idpessoa, idCidade,
                cpf, nome, apelido, rg,
                editEndereco.getText().toString(),
                editNumero.getText().toString(),
                editBairro.getText().toString(),
                editcomplemento.getText().toString(),
                Mask.unmask(editcep.getText().toString()),
                stringParaDate(datanascimento),
                editEmail.getText().toString(),
                stringParaDate(editDataUltima.getText().toString()),
                Double.parseDouble(editValorUltimacompra.getText().toString()),
                stringParaDate(editDataCadastro.getText().
                        toString())

        );
    }

    public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }


    //Date PickerDialog
    private int ano, mes, day;

    public void dataPicker() {
        dateTimeData();
        Calendar datapadrao = Calendar.getInstance();
        datapadrao.set(ano, mes, day);

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
                ano = datapadrao.get(Calendar.YEAR),
                mes = datapadrao.get(Calendar.MONTH),
                day = datapadrao.get(Calendar.DAY_OF_MONTH)

        );
        Calendar cMax = Calendar.getInstance();
        Calendar cMin = Calendar.getInstance();
        cMin.set(1900, 0, 1);
        cMax.set(cMax.get(Calendar.YEAR), mes, day);
        datePickerDialog.setMaxDate(cMax);
        datePickerDialog.setMinDate(cMin);

        datePickerDialog.setOnCancelListener(this);
        datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");

    }

    private void dateTimeData() {

        if (ano == 0) {
            Calendar c = Calendar.getInstance();
            ano = c.get(Calendar.YEAR);
            mes = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }
    }


    @Override
    public void onCancel(DialogInterface dialog) {

        ano = mes = day = 0;
        editDataNascimento.setText("");
        editDataAbertura.setText("");

    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int i, int i1, int i2) {

        Calendar tDefault = Calendar.getInstance();
        tDefault.set(ano, mes, day);

        ano = i;
        mes = i1;
        day = i2;

        editDataNascimento.setText((day < 10 ? "0" + day : day) + "/" +
                (mes + 1 < 10 ? "0" + (mes + 1) : mes + 1) + "/" +
                ano);

        editDataAbertura.setText((day < 10 ? "0" + day : day) + "/" +
                (mes + 1 < 10 ? "0" + (mes + 1) : mes + 1) + "/" +
                ano);


    }

    @Override
    public void onStop() {
        super.onStop();
        FormValidator.stopLiveValidation(getActivity());


    }

    public boolean Validate() {
        final boolean isValid = FormValidator.validate(this, new SimpleErrorPopupCallback(getActivity(), true));
        if (isValid) {
            iscpf = ValidaCpf(Mask.unmask(editCpf.getText().toString()));
            iscnpj = ValidaCnpj(Mask.unmask(editCnpj.getText().toString()));
            if (iscpf || iscnpj ){
                if (radioFisica.isChecked()){
                    if (cpfvalidaexiste()){
                        return true;
                    }
                }else if (radioJuridica.isChecked()){
                   if (cnpjvalidaexiste()){
                        return true;
                    }
                }
            }else if (!iscpf && radioFisica.isChecked()){
               editCpf.setError("CPF Inválido");
            }else if (!iscnpj && radioJuridica.isChecked()){
                editCnpj.setError("CNPJ Inválido");
            }

        } else {
            Toast.makeText(this.getContext(), "Preencher Campos Obrigatorios ", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    public void setPessoalt(Pessoa pessoaalt) {

        idpessoa = pessoaalt.getIdpessoa();
        idCidade = pessoaalt.getIdCidade();
        String cpf = pessoaalt.getCnpjCpf();

        if (cpf.length() == 11) {
            radioFisica.setChecked(true);
            radioJuridica.setChecked(false);

            RadioBoxFisica();

            editCpf.setText(cpf);
            editNome.setText(pessoaalt.getRazaoSocialNome());
            editApelido.setText(pessoaalt.getFantasiaApelido());
            editRg.setText(pessoaalt.getInscriEstadualRG());
            editDataNascimento.setText(ConvesorUtil.dateParaString(pessoaalt.getDataNascimento()));

        } else if (cpf.length() == 14) {

            radioJuridica.setChecked(true);
            radioFisica.setChecked(false);

            RadioBoxJuridica();

            editCnpj.setText(pessoaalt.getCnpjCpf());
            editRazaoSocial.setText(pessoaalt.getRazaoSocialNome());
            editFantasia.setText(pessoaalt.getFantasiaApelido());
            editInscricaEstadual.setText(pessoaalt.getInscriEstadualRG());
            editDataAbertura.setText(ConvesorUtil.dateParaString(pessoaalt.getDataNascimento()));
        }

        editEndereco.setText(pessoaalt.getEndereco());
        editNumero.setText(pessoaalt.getNumero());
        editcomplemento.setText(pessoaalt.getComplemento());
        editcep.setText(pessoaalt.getCep());
        editBairro.setText(pessoaalt.getBairro());
        editCidade.setText(cidadedao.ConsultaCidadeporid(pessoaalt.getIdCidade().toString()));
        editEmail.setText(pessoaalt.getEmail());
        editDataCadastro.setText(ConvesorUtil.dateParaString(pessoaalt.getDataCadastro()));

    }

    public Boolean ispessoAlt() {

        if (pessoaalt != null) {
            return true;
        }


        return false;
    }

    public Boolean cnpjvalidaexiste() {

        if (ispessoAlt()) {
            if (existeCpfCnpjAlterando(pessoaalt, editCnpj.getText().toString())) {
                editCnpj.setError("CNPJ já cadastrado");
                return false;
            }
        } else {
            if (existeCpfCnpjInserindo(Mask.unmask(editCnpj.getText().toString()))) {
                editCnpj.setError("CNPJ já cadastrado");
                return false;
            }
        }
        return true;
    }

    public Boolean cpfvalidaexiste() {

        if (ispessoAlt()) {
            if (existeCpfCnpjAlterando(pessoaalt, editCpf.getText().toString())) {
                editCpf.setError("CPF já cadastrado");
                return  false;
            }
        } else {
            if (existeCpfCnpjInserindo(Mask.unmask(editCpf.getText().toString()))) {
                editCpf.setError("CPF já cadastrado");
                return false;
            }
        }
        return true;
    }

    private Integer idpessoaexiste;
    public Boolean existeCpfCnpjAlterando(Pessoa pessoaalt, String CpfCnpj){

        idpessoaexiste = clientedao.CosultaClienteCNPJCPF(Mask.unmask(CpfCnpj));
        Integer idpessoaatualizando = pessoaalt.getIdpessoa();

               if (idpessoaexiste > 0)
                   if (!idpessoaexiste.equals(idpessoaatualizando)) return true;

        return false;

    }

    public Boolean existeCpfCnpjInserindo(String cpf){

        idpessoaexiste = clientedao.CosultaClienteCNPJCPF(cpf);

        if (idpessoaexiste > 0){
            return true;

        }

        return false;
    }

    public void LimparCampos() {

        editCpf.setText("");
        editCnpj.setText("");
        editNome.setText("");
        editRazaoSocial.setText("");
        editApelido.setText("");
        editFantasia.setText("");
        editRg.setText("");
        editcep.setText("");
        editInscricaEstadual.setText("");
        editDataNascimento.setText("");
        editDataAbertura.setText("");
        editEndereco.setText("");
        editNumero.setText("");
        editcomplemento.setText("");
        editBairro.setText("");
        editCidade.setText("");
        editEmail.setText("");
        editDataCadastro.setText("");

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void atualizar() {
        System.out.println("Fragment Pessoa");
    }


}





