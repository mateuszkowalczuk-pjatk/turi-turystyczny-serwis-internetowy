import { useTranslation } from 'react-i18next'
import { Link } from 'react-router-dom'
import FooterHeader from '../FooterHeader'
import FooterContent from '../FooterContent'
import FooterModule from '../FooterModule'
import TextExtraLight from '../../Controls/Text/TextExtraLight'
import TextRegular from '../../Controls/Text/TextRegular'
import FooterCopyright from '../FooterCopyright'
import styles from '../Footer.module.css'

const PremiumFooter = () => {
    const { t } = useTranslation()

    return (
        <div className={styles.wrapper}>
            <div className={styles.footer}>
                <FooterHeader />
                <FooterContent
                    content={
                        <FooterModule
                            title={<TextExtraLight text={t('footer.your-account')} />}
                            firstOption={
                                <Link
                                    to="/reservations"
                                    className={styles.link}
                                >
                                    <TextRegular text={t('footer.reservations')} />
                                </Link>
                            }
                            secondOption={
                                <Link
                                    to="/stays"
                                    className={styles.link}
                                >
                                    <TextRegular text={t('footer.stays')} />
                                </Link>
                            }
                            thirdOption={
                                <Link
                                    to="/favourites"
                                    className={styles.link}
                                >
                                    <TextRegular text={t('footer.favourites')} />
                                </Link>
                            }
                            fourthOption={
                                <Link
                                    to="/tourism"
                                    className={styles.link}
                                >
                                    <TextRegular text={t('footer.tourism')} />
                                </Link>
                            }
                            fifthOption={
                                <Link
                                    to="/profile"
                                    className={styles.link}
                                >
                                    <TextRegular text={t('footer.account')} />
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

export default PremiumFooter
