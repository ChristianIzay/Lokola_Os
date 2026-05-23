package com.muana.lokola.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log

/**
 * AppLauncher - Utilitaire pour ouvrir les applications système
 * 
 * Usage:
 * ```kotlin
 * val success = AppLauncher.launchDialer(context)
 * if (!success) Log.e("Launcher", "Impossible d'ouvrir le téléphone")
 * ```
 */
object AppLauncher {
    
    private const val TAG = "AppLauncher"

    /**
     * Lance une application par son nom de package
     * @return Boolean: true si succès, false sinon
     */
    fun launchApp(context: Context, packageName: String): Boolean {
        return try {
            val intent = context.packageManager.getLaunchIntentForPackage(packageName)
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                Log.d(TAG, "App lancée: $packageName")
                true
            } else {
                Log.w(TAG, "Pas d'intent trouvé pour: $packageName")
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "Erreur lancement app $packageName: ${e.message}")
            false
        }
    }

    /**
     * Ouvre le composeur téléphonique
     */
    fun launchDialer(context: Context): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            Log.d(TAG, "Composeur ouvert")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Erreur composeur: ${e.message}")
            false
        }
    }

    /**
     * Ouvre l'application Messages/SMS
     */
    fun launchMessages(context: Context): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_APP_MESSAGING)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            Log.d(TAG, "Messages ouverts")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Erreur messages: ${e.message}")
            // Fallback: essayer intent SMS
            return try {
                val fallbackIntent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:"))
                fallbackIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(fallbackIntent)
                true
            } catch (e2: Exception) {
                Log.e(TAG, "Fallback messages échoué: ${e2.message}")
                false
            }
        }
    }

    /**
     * Ouvre le navigateur Internet par défaut
     */
    fun launchBrowser(context: Context, url: String? = null): Boolean {
        return try {
            val intent = if (url != null) {
                Intent(Intent.ACTION_VIEW, Uri.parse(url))
            } else {
                Intent(Intent.ACTION_MAIN).apply {
                    addCategory(Intent.CATEGORY_APP_BROWSER)
                }
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            Log.d(TAG, "Navigateur ouvert")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Erreur navigateur: ${e.message}")
            false
        }
    }

    /**
     * Ouvre l'appareil photo
     */
    fun launchCamera(context: Context): Boolean {
        return try {
            val intent = Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            Log.d(TAG, "Caméra ouverte")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Erreur caméra: ${e.message}")
            false
        }
    }

    /**
     * Ouvre la galerie photos
     */
    fun launchGallery(context: Context): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            Log.d(TAG, "Galerie ouverte")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Erreur galerie: ${e.message}")
            false
        }
    }

    /**
     * Ouvre les contacts
     */
    fun launchContacts(context: Context): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            Log.d(TAG, "Contacts ouverts")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Erreur contacts: ${e.message}")
            false
        }
    }

    /**
     * Ouvre les paramètres Android
     */
    fun launchSettings(context: Context): Boolean {
        return try {
            val intent = Intent(android.provider.Settings.ACTION_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            Log.d(TAG, "Paramètres ouverts")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Erreur paramètres: ${e.message}")
            false
        }
    }

    /**
     * Vérifie si une application est installée
     */
    fun isAppInstalled(context: Context, packageName: String): Boolean {
        return try {
            context.packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    /**
     * Liste toutes les applications avec launcher
     */
    fun getInstalledAppsWithLauncher(context: Context): List<Pair<String, String>> {
        val packageManager = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        
        val apps = packageManager.queryIntentActivities(intent, 0)
        return apps.map { resolveInfo ->
            val packageName = resolveInfo.activityInfo.packageName
            val appName = resolveInfo.loadLabel(packageManager).toString()
            Pair(packageName, appName)
        }.sortedBy { it.second }
    }
}
