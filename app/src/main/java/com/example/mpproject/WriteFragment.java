package com.example.mpproject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

enum MemoMode {
    NEW,
    SHOW,
    EDIT,
    LOCK
}


public class WriteFragment extends Fragment implements IFrag {
    View vMain;
    Context context;
    ImageButton btnDate, btnSubmit, btnEditOption;
    FrameLayout frameDate;
    EditText edtTitle, edtContents;
    MemoDAO memoDAO;
    Memo memo;
    MemoMode mode;
    HGlobal hGlobal;

    public WriteFragment() {
        memo = new Memo();
        mode = MemoMode.NEW;
    }

    public WriteFragment(Memo memo) {
        this.memo = memo;
        this.mode = MemoMode.EDIT;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vMain = inflater.inflate(R.layout.fragment_write, container, false);
        context = container.getContext();
        init();

        return vMain;
    }

    void init() {
        memoDAO = MemoDAO.getInstance(context);
        hGlobal = HGlobal.getInstance();
        initView();
        initMemoMode();
        initLayoutListener();
    }

    void initView() {
        btnDate = vMain.findViewById(R.id.btnWriteDate);
        btnSubmit = vMain.findViewById(R.id.btnWriteSubmit);
        btnEditOption = vMain.findViewById(R.id.btnEditOption);
        frameDate = vMain.findViewById(R.id.frameDate);

        edtTitle = vMain.findViewById(R.id.edtTitle);
        edtContents = vMain.findViewById(R.id.edtContents);
    }

    void initMemoMode() {
        edtTitle.setText(memo.title);
        edtContents.setText(memo.contents);
    }

    void initLayoutListener() {
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        memo.setDate(year, month, day);
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(context , listener, memo.getYear(), memo.getMonth(), memo.getDay());
                dialog.show();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMemo();
                back();
            }
        });
        btnEditOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);
                popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.actionDelete) {
                            HUtils.showDeleteDialog(context,
                                    (dialog, which) -> {
                                        showMessage("삭제되었습니다");
                                        memoDAO.deleteMemo(memo);
                                        closeFragment();
                                    }, null);
                        } else {
                            HUtils.showLockDialog(context, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    showMessage(":"+ which);
                                }
                            }, null, null);
                        }
                        return true;
                    }
                });
                popup.show();//showing popup menu
            }
        });
    }

    void showLockDialog() {
        HUtils.showLockDialog(context, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showMessage(":"+ which);
            }
        }, null, null);
    }

    void saveMemo() {
        memo.title = edtTitle.getText().toString();
        memo.contents = edtContents.getText().toString();

        switch(mode) {
            case NEW:
                saveMemoNewMode();
                break;
            case EDIT:
                saveMemoEditMode();
                break;
        }
    }

    void saveMemoNewMode() {
        boolean titleIsEmpty, contentsIsEmpty;
        titleIsEmpty = memo.title.equals("");
        contentsIsEmpty = memo.contents.equals("");

        if (titleIsEmpty && contentsIsEmpty) {
            return;
        } else if (titleIsEmpty) {
            memo.title = HUtils.getDateFormat("MM월 dd일의 일기", memo.date);
        }

        memoDAO.addMemo(memo);
        showMessage("저장되었습니다");
    }

    void saveMemoEditMode() {
        boolean titleIsEmpty;
        titleIsEmpty = memo.title.equals("");

        if (titleIsEmpty) {
            memo.title = HUtils.getDateFormat("MM월 dd일의 일기", memo.date);
        }
        memoDAO.editMemo(memo);
        showMessage("저장되었습니다");
    }

    @Override
    public void onBackPressed() {
        saveMemo();
        back();
    }

    void back() {
        if (mode == MemoMode.NEW) {
            closeFragment();
        } else {
            changeShowFragment();
        }
    }

    void closeFragment() {
        MainActivity mainAct = ((MainActivity)getActivity());
        mainAct.changeMainFragment();
    }

    void changeShowFragment() {
        MainActivity mainAct = ((MainActivity)getActivity());
        mainAct.changeFragment(new ShowFragment(memo));
    }

    void showMessage(String text) {
        HUtils.showMessage(context, text);
    }
}























