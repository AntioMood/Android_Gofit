package com.example.gofit

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.gofit.databinding.ActivityInstrukturBotNavBinding
import com.example.gofit.menuInstruktur.profilInstruktur.ProfileInstrukturFragment
import com.example.gofit.menuMember.dashboard.DashboardFragment
import com.example.gofit.menuMember.home.HomeFragment
import com.example.gofit.menuMember.profileMember.ProfileMemberFragment

class InstrukturBotNavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInstrukturBotNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInstrukturBotNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val dashboardFragment = DashboardFragment()
        val profileFragment = ProfileInstrukturFragment()

        val bundle = intent.extras
        val id_user = bundle?.getString("id_user")

        val fragmentBundle = Bundle()
        fragmentBundle.putString("id", id_user)

        val nav: BottomNavigationView = binding.navView

        nav.setOnNavigationItemSelectedListener {
            if(it.itemId == R.id.navigation_home_instruktur){
                setCurrentFragment(homeFragment)
            }else if(it.itemId == R.id.navigation_dashboard_instruktur){
                setCurrentFragment(dashboardFragment)
            }else if(it.itemId == R.id.navigation_profile_instruktur){
                profileFragment.arguments = fragmentBundle
                setCurrentFragment(profileFragment)
            }
            true
        }
    }
    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment_activity_instruktur_bot_nav,fragment)
            commit()
        }
    }
}