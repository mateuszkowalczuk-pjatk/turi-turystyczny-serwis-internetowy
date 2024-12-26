import { useTranslation } from 'react-i18next'
import { Link } from 'react-router-dom'
import FooterHeader from '../FooterHeader'
import FooterContent from '../FooterContent'
import FooterModule from '../FooterModule'
import TextExtraLight from '../../Controls/Text/TextExtraLight'
import TextRegular from '../../Controls/Text/TextRegular'
import FooterCopyright from '../FooterCopyright'
import styles from '../Footer.module.css'

const GuestFooter = () => {
    const { t } = useTranslation()

    return (
        <div className={styles.wrapper}>
            <div className={styles.footer}>
                <FooterHeader />
                <FooterContent
                    content={
                        <FooterModule
                            title={<TextExtraLight text={t('footer.community')} />}
                            firstOption={
                                <Link
                                    to="/login"
                                    className={styles.link}
                                >
                                    <TextRegular text={t('footer.sign-in')} />
                                </Link>
                            }
                            secondOption={
                                <Link
                                    to="/register"
                                    className={styles.link}
                                >
                                    <TextRegular text={t('footer.sign-up')} />
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
