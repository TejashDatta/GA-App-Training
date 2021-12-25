package com.example.newsreader.add_news_source

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.newsreader.R

class AddNewsSourceFragment: Fragment(), AddNewsSourceContract.View {
  companion object {
    fun newInstance() = AddNewsSourceFragment()
  }

  override lateinit var presenter: AddNewsSourceContract.Presenter

  private lateinit var nameEditTextView: EditText
  private lateinit var urlEditTextView: EditText

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    val root = inflater.inflate(R.layout.fragment_add_news_source, container, false)

    root.apply {
      nameEditTextView = findViewById(R.id.nameEditText)
      urlEditTextView = findViewById(R.id.urlEditText)
    }

    nameEditTextView.addTextChangedListener { text: Editable? -> presenter.validateName(text.toString()) }
    urlEditTextView.addTextChangedListener { text: Editable? -> presenter.validateUrl(text.toString()) }

    return root
  }

  override fun setNameIsRequiredError() {
    nameEditTextView.error = getString(R.string.validate_required, getString(R.string.name))
  }

  override fun setNameIsTooLongError(maxLength: Int) {
    nameEditTextView.error = getString(R.string.validate_too_long, getString(R.string.name), maxLength)
  }

  override fun disableNameError() {
    nameEditTextView.error = null
  }

  override fun setUrlIsRequiredError() {
    urlEditTextView.error = getString(R.string.validate_required, getString(R.string.url))
  }

  override fun setUrlIsTooLongError(maxLength: Int) {
    urlEditTextView.error = getString(R.string.validate_too_long, getString(R.string.url), maxLength)
  }

  override fun setUrlFormatError() {
    urlEditTextView.error = getString(R.string.validate_not_url_format)
  }

  override fun disableUrlError() {
    urlEditTextView.error = null
  }

  override fun goBack() {
    TODO("Not yet implemented")
  }
}
