import { useTranslation } from 'react-i18next'
import FooterTitle from '../FooterTitle'
import FooterContent from '../FooterContent'
import FooterModule from '../FooterModule'
import TextExtraLight from '../../Controls/Text/TextExtraLight'
import TextRegular from '../../Controls/Text/TextRegular'
import FooterCopyright from '../FooterCopyright'
import { Link } from 'react-router-dom'
import styles from '../Footer.module.css'

const GuestFooter = () => {
    const { t } = useTranslation()

    return (
        <div className={styles.wrapper}>
            <div className={styles.footer}>
                <FooterTitle />
                <FooterContent
                    content={
                        <FooterModule
                            title={<TextExtraLight text={t('footer.become-turi-member')} />}
                            firstOption={
                                <Link
                                    to="/login"
                                    className={styles.link}
                                >
                                    <TextRegular text={t('footer.login')} />
                                </Link>
                            }
                            secondOption={
                                <Link
                                    to="/register"
                                    className={styles.link}
                                >
                                    <TextRegular text={t('footer.register')} />
                                </Link>
                            }
                        />
                    }
                />
                <FooterCopyright />
            </div>
        </div>
    )
}

export default GuestFooter
