import { ReactNode } from 'react'
import { useTranslation } from 'react-i18next'
import { Link } from 'react-router-dom'
import FooterModule from '../FooterModule'
import TextExtraLight from '../../Controls/Text/TextExtraLight'
import TextRegular from '../../Controls/Text/TextRegular'
import styles from './FooterContent.module.css'

const FooterContent = ({ content }: { content: ReactNode }) => {
    const { t } = useTranslation()

    return (
        <div className={styles.content}>
            {content}
            <FooterModule
                title={<TextExtraLight text={t('footer.offers')} />}
                firstOption={
                    <Link
                        to="/stays"
                        className={styles.link}
                    >
                        <TextRegular text={t('footer.stays')} />
                    </Link>
                }
                secondOption={
                    <Link
                        to="/attractions"
                        className={styles.link}
                    >
                        <TextRegular text={t('footer.attractions')} />
                    </Link>
                }
                thirdOption={
                    <Link
                        to="/rating"
                        className={styles.link}
                    >
                        <TextRegular text={t('footer.rating')} />
                    </Link>
                }
            />
            <FooterModule
                title={<TextExtraLight text={t('footer.more')} />}
                firstOption={
                    <Link
                        to="/about"
                        className={styles.link}
                    >
                        <TextRegular text={t('footer.about')} />
                    </Link>
                }
                secondOption={
                    <Link
                        to="/conditions"
                        className={styles.link}
                    >
                        <TextRegular text={t('footer.conditions')} />
                    </Link>
                }
                thirdOption={
                    <Link
                        to="/privacy"
                        className={styles.link}
                    >
                        <TextRegular text={t('footer.privacy')} />
                    </Link>
                }
                fourthOption={
                    <Link
                        to="/help"
                        className={styles.link}
                    >
                        <TextRegular text={t('footer.help')} />
                    </Link>
                }
            />
            <FooterModule
                title={<TextExtraLight text={t('footer.contact')} />}
                firstOption={<TextRegular text={t('footer.email')} />}
                secondOption={<TextRegular text={t('footer.phone-number')} />}
            />
        </div>
    )
}

export default FooterContent
