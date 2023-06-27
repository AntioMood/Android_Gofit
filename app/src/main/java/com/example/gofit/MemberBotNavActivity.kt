package com.example.gofit

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.gofit.databinding.ActivityMemberBotNavBinding
import com.example.gofit.menuMember.dashboard.DashboardFragment
import com.example.gofit.menuMember.home.HomeFragment
import com.example.gofit.menuMember.profileMember.ProfileMemberFragment

class MemberBotNavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMemberBotNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberBotNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val dashboardFragment = DashboardFragment()
        val profileFragment = ProfileMemberFragment()

        val bundle = intent.extras
        val id_user = bundle?.getString("id_user")

        val fragmentBundle = Bundle()
        fragmentBundle.putString("id", id_user)

        val nav: BottomNavigationView = binding.navView

        nav.setOnNavigationItemSelectedListener {
            if(it.itemId == R.id.navigation_home_member){
                setCurrentFragment(homeFragment)
            }else if(it.itemId == R.id.navigation_dashboard_member){
                setCurrentFragment(dashboardFragment)
            }else if(it.itemId == R.id.navigation_profile_member){
                profileFragment.arguments = fragmentBundle
                setCurrentFragment(profileFragment)
            }
            true
        }

//        val navView: BottomNavigationView = binding.navView
//
//        val navController = findNavController(R.id.nav_host_fragment_activity_member_bot_nav)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }

    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment_activity_member_bot_nav,fragment)
            commit()
        }
    }
}