import { useHooks } from '../../../../hooks/shared/useHooks.ts'
import FooterTitle from '../FooterTitle'
import TextRegular from '../../Controls/Text/TextRegular'
import FooterModule from '../FooterModule'
import FooterContent from '../FooterContent'
import TextExtraLight from '../../Controls/Text/TextExtraLight'
import FooterCopyright from '../FooterCopyright'
import { Link } from 'react-router-dom'
import styles from '../Footer.module.css'

const GuestFooter = () => {
    const { t } = useHooks()

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
