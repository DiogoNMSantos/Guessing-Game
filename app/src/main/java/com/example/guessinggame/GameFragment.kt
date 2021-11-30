package com.example.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.guessinggame.databinding.FragmentGameBinding
import androidx.lifecycle.Observer

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        viewModel.incorrectGuesses.observe(viewLifecycleOwner, Observer { it ->
            binding.incorrectGuesses.text = "Incorrect guesses: ${it}" })

        viewModel.livesLeft.observe(viewLifecycleOwner, Observer { it ->
            binding.lives.text = "You have ${it} lives left" })

        viewModel.secretWordDisplay.observe(viewLifecycleOwner, Observer { it ->
            binding.word.text = it })

        viewModel.gameOver.observe(viewLifecycleOwner, Observer { it ->
            if (it == true) {
                val action = GameFragmentDirections.actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        })

        binding.guessButton.setOnClickListener() {
            viewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}