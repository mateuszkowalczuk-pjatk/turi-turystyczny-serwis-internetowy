# Lokalna praca ze Stripe CLI
1. brew install stripe/stripe-cli/stripe 
2. stripe --version 
3. stripe login -> enter -> allow
4. stripe listen --forward-to localhost:8080/api/payment/webhook
5. Pobierz webhook z 'Ready! Your webhook signing secret is: whsec_12345abcdefg'
6. Ustaw webhook w application.yml: stripe.webhook-secret-key