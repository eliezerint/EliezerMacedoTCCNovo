package br.grupointegrado.appmetaforadevenda.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import net.i2p.android.ext.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.grupointegrado.appmetaforadevenda.Dao.PessoaDao;
import br.grupointegrado.appmetaforadevenda.Listagem.AdapterTelefone;
import br.grupointegrado.appmetaforadevenda.Pessoa.Pessoa;
import br.grupointegrado.appmetaforadevenda.Pessoa.Telefone;
import br.grupointegrado.appmetaforadevenda.R;
import br.grupointegrado.appmetaforadevenda.Util.FragmentTab;
import br.grupointegrado.appmetaforadevenda.Util.Mask;
import eu.inmite.android.lib.validations.form.FormValidator;
import eu.inmite.android.lib.validations.form.annotations.MaxLength;
import eu.inmite.android.lib.validations.form.annotations.MinLength;
import eu.inmite.android.lib.validations.form.annotations.NotEmpty;
import eu.inmite.android.lib.validations.form.callback.SimpleErrorPopupCallback;

/**
 * Created by eli on 18/09/2015.
 */
public class TelefoneFragment extends Fragment implements FragmentTab {

    private MaterialEditText campo_tel;

    private RecyclerView recyclerview_telefone;
    private RadioButton radiobt_comercial;
    private RadioButton radiobt_residencial;
    private RadioButton radiobt_celular;
    private FloatingActionButton bt_add_telefone;


    private String tipoTelefone;
    private String conteudoRadioButon;
    private Integer posicaolista;


    private List<Telefone> lista_telefone;
    private List<Telefone> lista_tel;

    private Telefone tel;


    private AdapterTelefone adaptertelefone;
    private Pessoa pessoaalt;
    private PessoaDao clientedao;
    private Telefone telefone;

    private boolean estadodofragment = false;
    private boolean telefonenaosalvo = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_telefone, container, false);


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerview_telefone = (RecyclerView) view.findViewById(R.id.recyclerview_telefone);
        bt_add_telefone = (FloatingActionButton) view.findViewById(R.id.bt_add_telefone);

        lista_telefone = new ArrayList<>();
        clientedao = new PessoaDao(this.getActivity());
        tel = new Telefone();

        final StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        llm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerview_telefone.setLayoutManager(llm);

        adaptertelefone = new AdapterTelefone(this.getActivity(), new ArrayList<Telefone>()) {
            @Override
            protected void onItemClickListener(int adapterPosition, int layoutPosition) {
                Telefone telefone = adaptertelefone.getItems().get(adapterPosition);

                AlterarTelefoneDialog(adapterPosition, telefone);

            }

            @Override
            protected boolean onLongItemClickListener(int adapterPosition, int layoutPosition) {


                return true;
            }
        };


        recyclerview_telefone.setAdapter(adaptertelefone);


        bt_add_telefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telefonenaosalvo = false;
                AddTelefoneDialogs(tel);
            }
        });
        FormValidator.startLiveValidation(this, new SimpleErrorPopupCallback(this.getActivity()));

    }

    @Override
    public void onStart() {
        super.onStart();

        pessoaalt = (Pessoa) getActivity().getIntent().getSerializableExtra("alterarpessoa");
        if (pessoaalt != null && estadodofragment == false) {
            estadodofragment = true;
            lista_telefone = clientedao.listTelefone(pessoaalt.getIdpessoa().toString());
            recyclerviewTelefone();
        }


    }


    public void recyclerviewTelefone() {
        
        adaptertelefone.setItems(lista_telefone);
        adaptertelefone.notifyDataSetChanged();

    }


    public Telefone getTelefonePreencherLista(String edit_telefone) {
        return new Telefone(edit_telefone, tipoTelefone

        );

    }


    public void AddTelefoneDialogs(final Telefone telefone) {

        MaterialDialog dialog = new MaterialDialog.Builder(this.getActivity())
                .title("Telefone")
                .customView(R.layout.layout_dialogs_telefone, true)
                .positiveText("Salvar")
                .negativeText("Sair")
                .autoDismiss(false)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {


                        tipoTelefone = conteudoRadioButon;


                        if (!campo_tel.getText().toString().isEmpty()) {
                            if (campo_tel.getText().length() >= 13) {
                                if (posicaolista != null) {
                                    lista_telefone.remove(lista_telefone.get(posicaolista));
                                }
                                lista_telefone.add(getTelefonePreencherLista(campo_tel.getText().toString()));
                                campo_tel.setText(" ");
                                recyclerviewTelefone();
                                telefonenaosalvo = false;

                                dialog.dismiss();
                            }else campo_tel.setError(" Telefone incompleto");

                        } else {
                            campo_tel.setError("Telefone não pode ser vazio");

                        }


                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();

                    }

                }).build();

        campo_tel = (MaterialEditText) dialog.getCustomView().findViewById(R.id.edit_telefone);
        TextWatcher telefonemask = Mask.insert("(##)#####-####", campo_tel);
        campo_tel.addTextChangedListener(telefonemask);

        radiobt_residencial = (RadioButton) dialog.getCustomView().findViewById(R.id.radiobt_residencial);
        radiobt_comercial = (RadioButton) dialog.getCustomView().findViewById(R.id.radiobt_comercial);
        radiobt_celular = (RadioButton) dialog.getCustomView().findViewById(R.id.radiobt_celular);

        if (telefone.getNumero() != null) {
            campo_tel.setText(telefone.getNumero());
            switch (telefone.getTipo()) {
                case "Residencial":
                    radiobt_residencial.setChecked(true);
                    conteudoRadioButon = "Residencial";
                    break;
                case "Comercial":
                    radiobt_comercial.setChecked(true);
                    conteudoRadioButon = "Comercial";
                    break;
                case "Celular":
                    radiobt_celular.setChecked(true);
                    conteudoRadioButon = "Celular";
                    break;
            }
        } else if (telefonenaosalvo) {

            campo_tel.setText(lista_telefone.get(posicaolista).getNumero());
            switch (lista_telefone.get(posicaolista).getTipo()) {
                case "Residencial":
                    radiobt_residencial.setChecked(true);
                    conteudoRadioButon = "Residencial";
                    break;
                case "Comercial":
                    radiobt_comercial.setChecked(true);
                    conteudoRadioButon = "Comercial";
                    break;
                case "Celular":
                    radiobt_celular.setChecked(true);
                    conteudoRadioButon = "Celular";
                    break;
            }
        } else {
            radiobt_residencial.setChecked(true);
            conteudoRadioButon = "Residencial";
        }

        radiobt_residencial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radiobt_comercial.setChecked(false);
                    radiobt_celular.setChecked(false);
                    conteudoRadioButon = "Residencial";
                }
            }
        });
        radiobt_comercial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radiobt_residencial.setChecked(false);
                    radiobt_celular.setChecked(false);
                    conteudoRadioButon = "Comercial";
                }
            }
        });
        radiobt_celular.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radiobt_comercial.setChecked(false);
                    radiobt_residencial.setChecked(false);
                    conteudoRadioButon = "Celular";
                }
            }
        });

        dialog.show();

    }

    public void AlterarTelefoneDialog(final Integer posicao, final Telefone telefone) {

        new MaterialDialog.Builder(this.getActivity())
                .title("Telefone")
                .items(R.array.Array_de_alterar)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                        if (text.equals("Editar")) {

                            if (telefone.getIdPessoa() != null) {
                                posicaolista = posicao;
                                AddTelefoneDialogs(telefone);
                            } else {
                                telefonenaosalvo = true;
                                posicaolista = posicao;
                                recyclerviewTelefone();
                                AddTelefoneDialogs(telefone);

                            }


                            dialog.dismiss();
                        } else if (text.equals("Excluir")) {
                            if (telefone.getIdPessoa() != null) {
                                lista_telefone.remove(lista_telefone.get(posicao));
                                recyclerviewTelefone();
                            } else {
                                lista_telefone.remove(lista_telefone.get(posicao));
                                recyclerviewTelefone();
                            }
                            dialog.dismiss();
                        }


                    }

                })
                .show();
    }

    public Telefone getTelefone(Integer idpessoa, String cpf, Integer x) {

        return new Telefone(idpessoa,
               (adaptertelefone.getItems().get(x).getNumero()),
                adaptertelefone.getItems().get(x).getTipo(), cpf);


    }

    public boolean telefoneSalvo(Integer pos) {
        if (lista_telefone.get(pos).getIdPessoa() != null)
            return true;


        return false;
    }


    public Integer tamanhoLista() {
        return lista_telefone.size();
    }

    public boolean Validate() {
        final boolean isValid = FormValidator.validate(this, new SimpleErrorPopupCallback(getActivity(), true));
        if (isValid) {
            return true;
        }


        return false;
    }

    public void LimparLista() {
        lista_telefone.clear();
        adaptertelefone.setItems(lista_telefone);
        adaptertelefone.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerviewTelefone();
    }

    @Override
    public void atualizar() {
        System.out.println("Fragment Telefone");
    }


}