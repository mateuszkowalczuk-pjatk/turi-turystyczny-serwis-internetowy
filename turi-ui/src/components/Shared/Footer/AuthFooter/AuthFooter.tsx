import { Trans } from 'react-i18next'
import FooterLanguage from '../FooterLanguage'
import FooterCopyright from '../FooterCopyright'
import { Link } from 'react-router-dom'
import styles from './AuthFooter.module.css'

const AuthFooter = () => {
    return (
        <div className={styles.footer}>
            <FooterLanguage />
            <p>
                <Trans
                    i18nKey="footer.auth-description"
                    components={{
                        strong: <strong />,
                        1: (
                            <Link
                                to="/info/terms-of-use"
                                className={styles.link}
                            />
                        ),
                        2: (
                            <Link
                                to="/info/privacy-policy"
                                className={styles.link}
                            />
                        )
                    }}
                />
            </p>
            <FooterCopyright />
        </div>
    )
}

export default AuthFooter
