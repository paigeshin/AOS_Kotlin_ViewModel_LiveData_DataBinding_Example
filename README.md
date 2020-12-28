`binding.myViewModel = viewModel`

`binding.lifecycleOwner = this`

`@={}` ⇒ 곧바로 값을 가져온다고 이해하면 편하다. 

# Basic Example, ViewModel with `DataBinding`

### On layout.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">
    
    <data>
        <variable
            name="myViewModel"
            type="com.anushka.viewmodeldemo1.MainActivityViewModel" />
    </data>
    
<androidx.constraintlayout.widget.ConstraintLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/count_text"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="66sp"
        android:textStyle="bold"
        android:typeface="serif"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.262" />

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Click Here"
        android:textSize="30sp"
        android:onClick="@{()-> myViewModel.updateCount()}"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```

### OnActivity

```kotlin
package com.anushka.viewmodeldemo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anushka.viewmodeldemo1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        /** Data Binding with ViewModel **/
        binding.myViewModel = viewModel

        viewModel.count.observe(this, Observer {
          binding.countText.text = it.toString()
        })

        
           
        
//        binding.button.setOnClickListener {
//           viewModel.updateCount()
//        }
// => This code is replaced by `android:onClick="@{()-> myViewModel.updateCount()}"`

    }
}
```

# Integrate LiveData with `DataBinding`

### On layout.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">
    
    <data>
        <variable
            name="myViewModel"
            type="com.anushka.viewmodeldemo1.MainActivityViewModel" />
    </data>
    
<androidx.constraintlayout.widget.ConstraintLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/count_text"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="66sp"
        android:textStyle="bold"
        android:typeface="serif"
        android:onClick="@{()-> myViewModel.updateCount()}"
        android:text="@{String.valueOf(myViewModel.count)}"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.262" />

    <!-- android:text="@{String.valueOf(myViewModel.count)}" -->
    <!-- This code is related with live data -->

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Click Here"
        android:textSize="30sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```

### On Activity

```kotlin
package com.anushka.viewmodeldemo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anushka.viewmodeldemo1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        /** Data Binding with LiveData **/
        binding.lifecycleOwner = this 

        /** Data Binding with ViewModel **/
        binding.myViewModel = viewModel

//        viewModel.count.observe(this, Observer {
//          binding.countText.text = it.toString()
//        })
        // => This code is replaced by `android:text="@{String.valueOf(myViewModel.count)}"`

//        binding.button.setOnClickListener {
//           viewModel.updateCount()
//        }
        // => This code is replaced by `android:onClick="@{()-> myViewModel.updateCount()}"`
    }
}
```

# Two Way DataBinding, `@=`

Using one-way data binding, you can set a value on an attribute and set a listener that reacts to a change in that attribute:

```xml
<CheckBox
    android:id="@+id/rememberMeCheckBox"
    android:checked="@{viewmodel.rememberMe}"
    android:onCheckedChanged="@{viewmodel.rememberMeChanged}"
/>
```

Two-way data binding provides a shortcut to this process:

```xml
<CheckBox
    android:id="@+id/rememberMeCheckBox"
    android:checked="@={viewmodel.rememberMe}"
/>
```

The **`@={}`** notation, which importantly includes the "=" sign, receives data changes to the property and listen to user updates at the same time.

In order to react to changes in the backing data, you can make your layout variable an implementation of **`Observable`**, usually **`[BaseObservable](https://developer.android.com/reference/androidx/databinding/BaseObservable)`**, and use a **`[@Bindable](https://developer.android.com/reference/androidx/databinding/Bindable)`** annotation, as shown in the following code snippet:

# Example Project

### layout.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">
    <data>
        <variable
            name="myViewModel"
            type="com.anushka.twowaydemo1.MainActivityViewModel" />
    </data>
<androidx.constraintlayout.widget.ConstraintLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/text_view"
        android:text="@={myViewModel.userName}"
        android:textSize="40sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintHorizontal_bias="0.525"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.663" />

    <EditText
        android:id="@+id/edit_text"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:ems="10"
        android:inputType="textPersonName"
        android:textSize="40sp"
        android:text="@={myViewModel.userName}"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.271" />

</androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```

### ViewModel

```kotlin
package com.anushka.twowaydemo1

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    val userName = MutableLiveData<String>()

    init {
        userName.value = "Frank"
    }

}
```

### Activity

```kotlin
package com.anushka.twowaydemo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.anushka.twowaydemo1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        //initialize view model which is in layout.xml file
        binding.myViewModel = viewModel

        //initialize livedata which is in layout.xml file, you don't need `observe` whe you apply this.
        binding.lifecycleOwner = this

    }

}
```

# When do we use Two Way Data Binding ?

- we can use one way data binding to show the user some data (app to user data flow).
- We can also use one way data binding to get user input(user to app data flow) .
- If, for some reason, we want to both show data and get user input over the same widget,

    in other words, if we need a **two-way data flow**, we should use two-way data binding .

    As an example in section 9 project example I have used two way data binding for an EditText.

# Final Project

### XML

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="mainActivityViewModel"
            type="com.anushka.viewmodeldemo2.MainActivityViewModel" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">

        <EditText
            android:id="@+id/input_edit_text"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:ems="10"
            android:inputType="number"
            android:textSize="36sp"
            android:text="@={mainActivityViewModel.inputText}"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintHorizontal_bias="0.323"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintVertical_bias="0.064" />

        <Button
            android:id="@+id/insert_button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="50dp"
            android:layout_marginTop="40dp"
            android:text="Add"
            android:textSize="36sp"
            android:onClick="@{()->mainActivityViewModel.setTotal()}"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/input_edit_text" />

        <TextView
            android:id="@+id/result_text_view"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="45dp"
            android:layout_marginTop="32dp"
            android:layout_marginEnd="41dp"
            android:textSize="36sp"
            android:text="@{String.valueOf(mainActivityViewModel.totalData)}"
            app:layout_constraintEnd_toEndOf="@+id/insert_button"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/insert_button" />

    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```

### ViewModel

```kotlin
package com.anushka.viewmodeldemo2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel(startingTotal : Int) : ViewModel() {
     private var total = MutableLiveData<Int>()
     val totalData : LiveData<Int>
     get() = total

//    @Bindable
    val inputText = MutableLiveData<String>()

    init {
        total.value = startingTotal
    }

    fun setTotal(){
        val intInput: Int = inputText.value!!.toInt()
        total.value =(total.value)?.plus(intInput)
    }
}
```

### ViewModel Factory

```kotlin
package com.anushka.viewmodeldemo2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MainActivityViewModelFactory(private val startingTotal : Int) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)){
            return MainActivityViewModel(startingTotal) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}
```

### Activity

```kotlin
package com.anushka.viewmodeldemo2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anushka.viewmodeldemo2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var viewModelFactory: MainActivityViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModelFactory = MainActivityViewModelFactory(125)
        viewModel = ViewModelProvider(this,viewModelFactory).get(MainActivityViewModel::class.java)

        binding.mainActivityViewModel = viewModel //viewmodel
        binding.lifecycleOwner = this //live data

//        viewModel.totalData.observe(this, Observer {
//            binding.resultTextView.text = it.toString()
//        })

//        binding.insertButton.setOnClickListener {
//            viewModel.setTotal(binding.inputEditText.text.toString().toInt())
//        }
    }
}
```